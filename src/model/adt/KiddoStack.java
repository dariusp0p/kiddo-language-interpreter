package model.adt;

public interface KiddoStack<T> extends Iterable<T> {
    void push(T item);
    T pop();
    T peek();
    void clear();
    boolean isEmpty();
    int size();
}
