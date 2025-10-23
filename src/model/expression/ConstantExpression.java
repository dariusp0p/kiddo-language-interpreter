package model.expression;

import model.state.SymbolTable;
import model.value.Value;

public record ConstantExpression(Value value) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable) { return value; }

    @Override
    public String toString() { return value.toString(); }
}
