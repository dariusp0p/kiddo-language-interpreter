package model.state;

import model.adt.KiddoStack;
import model.adt.KiddoArrayStack;
import model.statement.Statement;
import utilities.ExecutionStackException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DequeExecutionStack implements ExecutionStack {
    private final KiddoStack<Statement> stack = new KiddoArrayStack<>();

    @Override
    public void push(Statement element) {
        Objects.requireNonNull(element, "element");
        stack.push(element);
    }

    @Override
    public Statement pop() {
        return stack.pop();
    }

    @Override
    public Statement peek() {
        Statement e = stack.peek();
        if (e == null) throw new ExecutionStackException("Execution stack is empty!");
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
    public String toString() {
        List<Statement> snapshot = new ArrayList<>();
        for (Statement statement : stack) {
            snapshot.add(statement);
        }
        return snapshot.toString();
    }
}
