package model.expression;

import exceptions.AdtException;
import exceptions.ExpressionException;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.Value;

public record VariableExpression(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) throws ExpressionException {
        try {
            return symbolTable.lookup(variableName);
        } catch (AdtException e) {
            throw new ExpressionException("Variable \"" + variableName + "\" is not defined", e);
        }
    }

    @Override
    public String toString() { return variableName; }
}
