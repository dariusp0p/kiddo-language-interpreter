package model.adt;

import exceptions.AdtException;

import java.util.*;

public class KiddoHashMapDictionary<K, V> implements KiddoDictionary<K, V> {
    private final HashMap<K, V> data = new HashMap<>();

    public KiddoHashMapDictionary() {
        // empty map no failure
    }
    public KiddoHashMapDictionary(Map<K, V> initial) throws AdtException {
        if (initial == null) throw new AdtException("Initial must not be null");
        data.putAll(initial);
    }

    @Override
    public void put(K key, V value) throws AdtException {
        if (key == null) throw new AdtException("Key must not be null");
        if (value == null) throw new AdtException("Value must not be null");
        data.put(key, value);
    }

    @Override
    public V get(K key) throws AdtException {
        if (key == null) throw new AdtException("Key must not be null");
        if (!data.containsKey(key)) throw new AdtException("Key not found: " + key);
        return data.get(key);
    }

    @Override
    public V remove(K key) throws AdtException {
        if (key == null) throw new AdtException("Key must not be null");
        if (!data.containsKey(key)) throw new AdtException("Key not found: " + key);
        return data.remove(key);
    }

    @Override
    public boolean containsKey(K key) throws AdtException {
        if (key == null) throw new AdtException("Key must not be null");
        return data.containsKey(key);
    }

    @Override
    public void clear() {
        data.clear();
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
    public List<V> values() {
        return List.copyOf(data.values());
    }

    @Override
    public Map<K, V> toMap() {
        return Collections.unmodifiableMap(new HashMap<>(data));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        boolean first = true;

        for (Map.Entry<K, V> entry : data.entrySet()) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(entry.getKey()).append(" = ").append(entry.getValue());
        }

        sb.append(" }");
        return sb.toString();
    }
}
