package model.state;

import model.statement.Statement;

public interface ExecutionStack {
    void push(Statement element);
    Statement pop();
    Statement peek();
    boolean isEmpty();
    int size();
    void clear();
}
