package model.adt;

import utilities.DictionaryException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class KiddoHashMapDictionary<K, V> implements KiddoDictionary<K, V> {
    private final HashMap<K, V> data = new HashMap<K, V>();

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
}
