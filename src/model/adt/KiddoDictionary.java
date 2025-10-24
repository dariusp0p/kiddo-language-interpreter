package model.adt;

import java.util.Set;

public interface KiddoDictionary<K, V> {
    void put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    boolean isEmpty();
    int size();
    Set<K> keySet();
}
