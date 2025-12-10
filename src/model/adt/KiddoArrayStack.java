package model.adt;

import exceptions.AdtException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class KiddoArrayStack<T> implements KiddoStack<T> {
    private final Deque<T> data = new ArrayDeque<>();

    @Override
    public void push(T value) throws AdtException {
        if (value == null) throw new AdtException("Value must not be null");
        data.push(value);
    }

    @Override
    public T pop() throws AdtException {
        if (data.isEmpty()) throw new AdtException("The stack is empty");
        return data.pop();
    }

    @Override
    public T peek() throws AdtException {
        if (data.isEmpty()) throw new AdtException("The stack is empty");
        return data.peek();
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
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public List<T> asList() {
        return new java.util.ArrayList<>(data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (T value : data) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(value);
        }
        return sb.toString();
    }
}
