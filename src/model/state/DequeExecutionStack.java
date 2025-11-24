package model.state;

import exceptions.AdtException;
import model.adt.KiddoStack;
import model.adt.KiddoArrayStack;
import model.statement.Statement;

public class DequeExecutionStack implements ExecutionStack {
    private final KiddoStack<Statement> stack = new KiddoArrayStack<>();

    @Override
    public void push(Statement element) throws AdtException {
        stack.push(element);
    }

    @Override
    public Statement pop() throws AdtException {
        return stack.pop();
    }

    @Override
    public Statement peek() throws AdtException {
        return stack.peek();
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
    public String toString() {
        return stack.toString();
    }
}
