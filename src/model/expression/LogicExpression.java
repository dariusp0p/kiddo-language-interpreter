package model.expression;

import exceptions.ExpressionException;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.BooleanValue;
import model.value.Value;

public record LogicExpression(Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) throws ExpressionException {
        Value leftTerm;
        Value rightTerm;

        try {
            leftTerm = left.evaluate(symbolTable, heapTable);
        } catch (ExpressionException e) {
            throw new ExpressionException("Failed to evaluate left operand of (" + left + " " + operator + " " + right + ")", e);
        }

        if (!(leftTerm instanceof BooleanValue(boolean value))) {
            throw new ExpressionException("Left operand is not a boolean: " + leftTerm);
        }

        try {
            rightTerm = right.evaluate(symbolTable, heapTable);
        } catch (ExpressionException e) {
            throw new ExpressionException("Failed to evaluate right operand of (" + left + " " + operator + " " + right + ")", e);
        }

        if (!(rightTerm instanceof BooleanValue(boolean value1))) {
            throw new ExpressionException("Right operand is not a boolean: " + rightTerm);
        }

        return switch (operator) {
            case "and" -> new BooleanValue(value && value1);
            case "or"  -> new BooleanValue(value || value1);
            default -> throw new ExpressionException("Unknown logical operator: " + operator);
        };
    }
}
