package model.expression;

import exceptions.ExpressionException;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.Value;
import model.type.Type;

public record ConstantExpression(Value value) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) { return value; }

    @Override
    public Type typecheck(SymbolTable typeEnv) throws ExpressionException {
        return value.getType();
    }

    @Override
    public String toString() { return value.toString(); }
}
