package model.statement;

import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;
import utilities.StatementException;

public record VariableDeclarationStatement(Type type, String variableName) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        SymbolTable symbolTable = programState.symbolTable();
        if (symbolTable.isDefined(variableName)) throw new StatementException("Variable already defined!");
        symbolTable.define(variableName, type.getDefaultValue());
        return programState;
    }
}
