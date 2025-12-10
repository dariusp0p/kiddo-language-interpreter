package model.adt;

import exceptions.AdtException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KiddoArrayList<T> implements KiddoList<T> {
    private final ArrayList<T> data = new ArrayList<>();

    @Override
    public void add(T value) throws AdtException {
        if (value == null) throw new AdtException("Value must not be null");
        data.add(value);
    }

    @Override
    public T get(int index) throws AdtException {
        if (invalidIndex(index)) throw new AdtException("Index out of bounds: " + index);
        return data.get(index);
    }

    @Override
    public T remove(int index) throws AdtException {
        if (invalidIndex(index)) throw new AdtException("Index out of bounds: " + index);
        return data.remove(index);
    }

    @Override
    public void set(int index, T value) throws AdtException {
        if (value == null) throw new AdtException("Value must not be null");
        if (invalidIndex(index)) throw new AdtException("Index out of bounds: " + index);
        data.set(index, value);
    }

    @Override
    public boolean contains(T value) throws AdtException {
        if (value == null) throw new AdtException("Value must not be null");
        return data.contains(value);
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
    public void clear() {
        data.clear();
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public List<T> asList() {
        return Collections.unmodifiableList(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    private boolean invalidIndex(int index) {
        return (index < 0 || index >= data.size());
    }
}
