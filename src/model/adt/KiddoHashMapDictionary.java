package model.adt;

import utilities.DictionaryException;

import java.util.*;

public class KiddoHashMapDictionary<K, V> implements KiddoDictionary<K, V> {
    private final HashMap<K, V> data = new HashMap<K, V>();

    public KiddoHashMapDictionary(Map<K, V> integerValueMap) {
        if (integerValueMap == null) {
            throw new DictionaryException("Input map must not be null");
        }
        this.data.putAll(integerValueMap);
    }

    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new DictionaryException("Key and value must not be null");
        }
        data.put(key, value);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new DictionaryException("Key must not be null");
        }
        if (!data.containsKey(key)) {
            throw new DictionaryException("Key not found: " + key);
        }
        return data.get(key);
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new DictionaryException("Key must not be null");
        }
        if (!data.containsKey(key)) {
            throw new DictionaryException("Key not found: " + key);
        }
        return data.remove(key);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new DictionaryException("Key must not be null");
        }
        return data.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(data.keySet());
    }

    @Override
    public List<V> values() { return List.copyOf(data.values()); }

    @Override
    public HashMap<K, V> toMap() {
        return new HashMap<>(data);
    }
    @Override
    public String toString() {
        return data.toString();
    }
}
