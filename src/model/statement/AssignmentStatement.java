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

        Value value = expression.evaluate(symbolTable, programState.heapTable());
        var declaredType = symbolTable.getType(variableName);

        if (!value.getType().equals(declaredType))
            throw new StatementException("Type mismatch: variable `" + variableName + "` has type "
                    + declaredType + " but expression evaluated to " + value.getType());

        symbolTable.update(variableName, value);
        return programState;
    }
}
