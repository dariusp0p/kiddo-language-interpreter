package model.adt;

import exceptions.AdtException;

public interface KiddoStack<T> extends Iterable<T> {
    void push(T item) throws AdtException;
    T pop() throws AdtException;
    T peek() throws AdtException;
    void clear();
    boolean isEmpty();
    int size();
}
