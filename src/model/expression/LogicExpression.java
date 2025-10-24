package model.expression;

import model.state.SymbolTable;
import model.type.Type;
import model.value.BooleanValue;
import model.value.Value;
import utilities.ExpressionException;

public record LogicExpression
        (Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable) {
        var leftTerm = left.evaluate(symbolTable);
        if (isNotBoolean(leftTerm)) throw new ExpressionException(String.format("%s is not a number!", leftTerm));
        var leftValue = (BooleanValue) leftTerm;
        var rightTerm = right.evaluate(symbolTable);
        if (isNotBoolean(rightTerm)) throw new ExpressionException(String.format("%s is not a number!", rightTerm));
        var rightValue = (BooleanValue) rightTerm;

        return switch (operator) {
            case "and" -> new BooleanValue(leftValue.getValue() && rightValue.getValue());
            case "or" -> new BooleanValue(leftValue.getValue() || rightValue.getValue());
            default -> throw new ExpressionException("Unknown operator: " + operator);
        };
    }

    private boolean isNotBoolean(Value term) {
        return term.getType() != Type.BOOLEAN;
    }
}
