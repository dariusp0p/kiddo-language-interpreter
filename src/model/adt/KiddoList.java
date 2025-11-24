package model.adt;

import exceptions.AdtException;

import java.util.Iterator;
import java.util.List;

public interface KiddoList<T> extends Iterable<T> {
    void add(T value) throws AdtException;
    T get(int index) throws AdtException;
    T remove(int index) throws AdtException;
    void set(int index, T value) throws AdtException;
    boolean contains(T value) throws AdtException;
    boolean isEmpty();
    int size();
    void clear();
    Iterator<T> iterator();
    List<T> asList();
}
