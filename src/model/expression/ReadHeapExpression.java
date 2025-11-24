package model.expression;

import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.ReferenceValue;
import model.value.Value;

public record ReadHeapExpression(Expression expression) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) {
        Value evaluatedValue = expression.evaluate(symbolTable, heapTable);

        if (!(evaluatedValue instanceof ReferenceValue refValue)) {
            throw new RuntimeException("Expression does not evaluate to a RefValue.");
        }

        int address = refValue.address();

        if (!heapTable.contains(address)) {
            throw new RuntimeException("Address " + address + " is not defined in the Heap.");
        }

        return heapTable.get(address);
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}
