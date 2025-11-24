package model.state;

public record ProgramState
        (ExecutionStack executionStack, SymbolTable symbolTable, Output output, FileTable fileTable, HeapTable heapTable) {

    @Override
    public String toString() {
        return "=== ProgramState ===\n" +
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
