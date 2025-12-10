package model.adt;

import exceptions.AdtException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface KiddoDictionary<K, V> {
    void put(K key, V value) throws AdtException;
    V get(K key) throws AdtException;
    V remove(K key) throws AdtException;
    boolean containsKey(K key) throws AdtException;
    void clear();
    boolean isEmpty();
    int size();
    Set<K> keySet();
    List<V> values();
    Map<K, V> toMap();
}
