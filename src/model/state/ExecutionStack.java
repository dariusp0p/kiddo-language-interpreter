package model.state;

import exceptions.AdtException;
import model.statement.Statement;

public interface ExecutionStack {
    void push(Statement element) throws AdtException;
    Statement pop() throws AdtException;
    Statement peek() throws AdtException;
    boolean isEmpty();
    int size();
    void clear();
}
