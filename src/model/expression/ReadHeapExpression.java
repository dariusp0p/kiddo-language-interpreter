package model.expression;

import exceptions.AdtException;
import exceptions.ExpressionException;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.ReferenceValue;
import model.value.Value;

public record ReadHeapExpression(Expression expression) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) throws ExpressionException {
        Value evaluatedValue;
        try {
            evaluatedValue = expression.evaluate(symbolTable, heapTable);
        } catch (ExpressionException e) {
            throw new ExpressionException("Failed to evaluate inner expression in rH(" + expression + ")", e);
        }

        if (!(evaluatedValue instanceof ReferenceValue refValue)) {
            throw new ExpressionException("rH applied to non-reference value: " + evaluatedValue);
        }

        int address = refValue.address();

        try {
            if (!heapTable.contains(address)) {
                throw new ExpressionException("Address " + address + " is not allocated in the heap.");
            }
            return heapTable.get(address);

        } catch (AdtException e) {
            throw new ExpressionException("Heap access failed at address " + address, e);
        }
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}
