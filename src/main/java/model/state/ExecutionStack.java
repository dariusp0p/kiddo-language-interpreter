package model.state;

import exceptions.AdtException;
import model.statement.Statement;

import java.util.List;

public interface ExecutionStack {
    void push(Statement element) throws AdtException;
    Statement pop() throws AdtException;
    Statement peek() throws AdtException;
    boolean isEmpty();
    int size();
    void clear();
    List<Statement> getAll() throws AdtException;
}
