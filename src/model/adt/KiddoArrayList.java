package model.adt;

import utilities.ListException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KiddoArrayList<T> implements KiddoList<T> {
    private final ArrayList<T> data = new ArrayList<>();

    @Override
    public void add(T value) {
        if (value == null) {
            throw new ListException("Cannot add null to the list");
        }
        data.add(value);
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return data.get(index);
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return data.remove(index);
    }

    @Override
    public void set(int index, T value) {
        if (value == null) {
            throw new ListException("Cannot set null in the list");
        }
        checkIndex(index);
        data.set(index, value);
    }

    @Override
    public boolean contains(T value) {
        if (value == null) {
            throw new ListException("Value must not be null");
        }
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
    public List<T> asList() {
        return Collections.unmodifiableList(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= data.size()) {
            throw new ListException("Index out of bounds: " + index);
        }
    }
}
