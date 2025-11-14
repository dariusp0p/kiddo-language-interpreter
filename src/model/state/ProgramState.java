package model.state;

import model.statement.Statement;

public record ProgramState
        (ExecutionStack executionStack, SymbolTable symbolTable, Output output, FileTable fileTable, Statement originalProgram) {

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
                "OriginalProgram: " +
                (originalProgram == null ? "null" : originalProgram.toString()) + "\n";
    }
}
