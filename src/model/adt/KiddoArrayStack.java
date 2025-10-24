package model.adt;

import utilities.StackException;
import java.util.ArrayDeque;
import java.util.Deque;

public class KiddoArrayStack<T> implements KiddoStack<T> {
    private final Deque<T> data = new ArrayDeque<>();

    @Override
    public void push(T value) {
        if (value == null) {
            throw new StackException("Cannot push null onto the stack!");
        }
        data.push(value);
    }

    @Override
    public T pop() {
        if (data.isEmpty()) {
            throw new StackException("Cannot pop from empty stack!");
        }
        return data.pop();
    }

    @Override
    public T peek() {
        T top = data.peek();
        if (top == null) {
            throw new StackException("Cannot peek from empty stack!");
        }
        return top;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }
}
