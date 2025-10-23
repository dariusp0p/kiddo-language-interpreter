package model.state;

import model.statement.Statement;

import java.util.*;

public class DequeExecutionStack implements ExecutionStack {
    private final Deque<Statement> stack = new ArrayDeque<>();

    @Override
    public void push(Statement element) {
        Objects.requireNonNull(element, "element");
        stack.push(element);
    }

    @Override
    public Statement pop() {
        try {
            return stack.pop();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Execution stack is empty");
        }
    }

    @Override
    public Statement peek() {
        Statement e = stack.peek();
        if (e == null) throw new NoSuchElementException("Execution stack is empty");
        return e;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public Iterator<Statement> iterator() {
        return stack.iterator();
    }

    @Override
    public String toString() {
        List<Statement> snapshot = new ArrayList<>(stack);
        return snapshot.toString();
    }
}
