package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;

public record VariableDeclarationStatement(Type type, String variableName) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        SymbolTable symbolTable = programState.symbolTable();

        try {
            if (symbolTable.isDefined(variableName)) throw new StatementException("Variable \"" + variableName + "\" is already defined");
            symbolTable.define(variableName, type.getDefaultValue());
        } catch (AdtException e) {
            throw new StatementException("Failed to declare variable \"" + variableName + "\" of type " + type, e);
        }

        return programState;
    }

    @Override
    public String toString() {
        return type + " " + variableName;
    }
}
