package model.expression;

import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, HeapTable heapTable);
}
