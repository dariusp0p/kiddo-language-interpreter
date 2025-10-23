package model.state;

import model.statement.Statement;

public record ProgramState
        (ExecutionStack executionStack, SymbolTable symbolTable, Output output, Statement originalProgram) {

    @Override
    public String toString() {
        return "ProgramState:\n" +
                "ExecutionStack:\n" +
                (executionStack == null ? "null" : executionStack.toString()) + "\n" +
                "SymbolTable:\n" +
                (symbolTable == null ? "null" : symbolTable.toString()) + "\n" +
                "Output:\n" +
                (output == null ? "null" : output.toString()) + "\n" +
                "OriginalProgram:\n" +
                (originalProgram == null ? "null" : originalProgram.toString()) + "\n";
    }
}

