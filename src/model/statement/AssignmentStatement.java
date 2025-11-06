package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.Value;
import utilities.StatementException;

public record AssignmentStatement(Expression expression, String variableName) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        SymbolTable symbolTable = programState.symbolTable();
        if (!symbolTable.isDefined(variableName)) throw new StatementException("Variable not defined!");
        Value value = expression.evaluate(symbolTable);
        if (!value.getType().equals(symbolTable.getType(variableName))) throw new StatementException("Type mismatch!");
        symbolTable.update(variableName, value);
        return programState;
    }
}
