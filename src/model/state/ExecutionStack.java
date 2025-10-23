package model.state;

import model.statement.Statement;

import java.util.Iterator;

public interface ExecutionStack extends Iterable<Statement> {
    void push(Statement element);
    Statement pop();
    Statement peek();
    boolean isEmpty();
    int size();
    void clear();

    @Override
    Iterator<Statement> iterator();
}
