package model.state;

import exceptions.KiddoException;
import model.statement.Statement;

public record ProgramState
        (int id, ExecutionStack executionStack, SymbolTable symbolTable, Output output, FileTable fileTable, HeapTable heapTable) {

    private static int lastId = 0;

    private static synchronized int getNewId() {
        return ++lastId;
    }

    public ProgramState(ExecutionStack executionStack,
                        SymbolTable symbolTable,
                        Output output,
                        FileTable fileTable,
                        HeapTable heapTable) {
        this(getNewId(), executionStack, symbolTable, output, fileTable, heapTable);
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws KiddoException {
        if (executionStack.isEmpty()) throw new KiddoException("Execution stack is empty.");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return "=== ProgramState ===\n" +
                "ID: " +
                id + "\n" +
                "ExecutionStack: " +
                (executionStack == null ? "null" : executionStack.toString()) + "\n" +
                "SymbolTable: " +
                (symbolTable == null ? "null" : symbolTable.toString()) + "\n" +
                "Output: " +
                (output == null ? "null" : output.toString()) + "\n" +
                "FileTable: " +
                (fileTable == null ? "null" : fileTable.toString()) + "\n" +
                "HeapTable: " +
                (heapTable == null ? "null" : heapTable.toString()) + "\n";
    }
}
