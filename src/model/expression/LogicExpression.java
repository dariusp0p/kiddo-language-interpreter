package model.expression;

import model.state.HeapTable;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.Value;
import utilities.ExpressionException;

public record LogicExpression
        (Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, HeapTable heapTable) {
        var leftTerm = left.evaluate(symbolTable, heapTable);
        if (isNotBoolean(leftTerm)) throw new ExpressionException(String.format("%s is not a number!", leftTerm));
        var leftValue = (BooleanValue) leftTerm;
        var rightTerm = right.evaluate(symbolTable, heapTable);
        if (isNotBoolean(rightTerm)) throw new ExpressionException(String.format("%s is not a number!", rightTerm));
        var rightValue = (BooleanValue) rightTerm;

        return switch (operator) {
            case "and" -> new BooleanValue(leftValue.value() && rightValue.value());
            case "or" -> new BooleanValue(leftValue.value() || rightValue.value());
            default -> throw new ExpressionException("Unknown operator: " + operator);
        };
    }

    private boolean isNotBoolean(Value term) {
        return !(term.getType() instanceof BooleanType);
    }
}
