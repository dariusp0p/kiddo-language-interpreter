package model.expression;

import exceptions.ExpressionException;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.IntegerValue;
import model.value.Value;

public record ArithmeticExpression(Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) throws ExpressionException {
        Value leftTerm;
        Value rightTerm;

        try {
            leftTerm = left.evaluate(symbolTable, heapTable);
        } catch (ExpressionException e) {
            throw new ExpressionException("Failed to evaluate left operand in (" + left + " " + operator + " " + right + ")", e);
        }

        if (!(leftTerm instanceof IntegerValue(int value))) {
            throw new ExpressionException("Left operand is not an integer: " + leftTerm);
        }

        try {
            rightTerm = right.evaluate(symbolTable, heapTable);
        } catch (ExpressionException e) {
            throw new ExpressionException("Failed to evaluate right operand in (" + left + " " + operator + " " + right + ")", e);
        }

        if (!(rightTerm instanceof IntegerValue(int value1))) {
            throw new ExpressionException("Right operand is not an integer: " + rightTerm);
        }

        if (operator == null) {
            throw new ExpressionException("Operator must not be null");
        }

        if ("/".equals(operator) && value1 == 0) {
            throw new ExpressionException("Division by zero in expression: (" + left + " / " + right + ")");
        }

        return switch (operator) {
            case "+" -> new IntegerValue(value + value1);
            case "-" -> new IntegerValue(value - value1);
            case "*" -> new IntegerValue(value * value1);
            case "/" -> new IntegerValue(value / value1);
            default  -> throw new ExpressionException("Unknown arithmetic operator: " + operator);
        };
    }
}
