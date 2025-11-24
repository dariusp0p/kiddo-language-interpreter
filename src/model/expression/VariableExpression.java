package model.expression;

import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.Value;

public record VariableExpression(String variableName) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) {
        return symbolTable.lookup(variableName);
    }

    @Override
    public String toString() { return variableName; }
}
