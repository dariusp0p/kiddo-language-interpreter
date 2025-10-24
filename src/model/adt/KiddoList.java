package model.adt;

import java.util.List;

public interface KiddoList<T> {
    void add(T value);
    T get(int index);
    T remove(int index);
    void set(int index, T value);
    boolean contains(T value);
    boolean isEmpty();
    int size();
    List<T> asList();
}
