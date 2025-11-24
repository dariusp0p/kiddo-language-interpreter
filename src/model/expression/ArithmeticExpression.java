package model.expression;

import model.state.HeapTable;
import model.state.SymbolTable;
import model.type.IntegerType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;
import utilities.ExpressionException;

public record ArithmeticExpression
        (Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) {
        var leftTerm = left.evaluate(symbolTable,  heapTable);
        if (isNotInteger(leftTerm)) throw new ExpressionException(String.format("%s is not a number!", leftTerm));
        var leftValue = (IntegerValue) leftTerm;
        var rightTerm = right.evaluate(symbolTable, heapTable);
        if (isNotInteger(rightTerm)) throw new ExpressionException(String.format("%s is not a number!", rightTerm));
        var rightValue = (IntegerValue) rightTerm;

        if ("/".equals(operator) && rightValue.value() == 0) {
            throw new ExpressionException("Division by zero!");
        }

        return switch (operator) {
            case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
            case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
            case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
            case "/" -> new IntegerValue(leftValue.value() / rightValue.value());
            default -> throw new ExpressionException("Unknown operator: " + operator);
        };
    }

    private boolean isNotInteger(Value term) {
        return !(term.getType() instanceof IntegerType);
    }
}
