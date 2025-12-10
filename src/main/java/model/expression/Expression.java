package model.expression;

import exceptions.ExpressionException;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, HeapTable heapTable) throws ExpressionException;
    Type typecheck(SymbolTable typeEnv) throws ExpressionException;
}
