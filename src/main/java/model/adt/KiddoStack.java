package model.adt;

import exceptions.AdtException;

import java.util.List;

public interface KiddoStack<T> extends Iterable<T> {
    void push(T item) throws AdtException;
    T pop() throws AdtException;
    T peek() throws AdtException;
    void clear();
    boolean isEmpty();
    int size();
    List<T> asList();
}
