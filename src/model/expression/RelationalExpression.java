package model.expression;

import model.state.SymbolTable;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;
import utilities.ExpressionException;

public record RelationalExpression
        (Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable) {
        var leftTerm = left.evaluate(symbolTable);
        if (isNotInteger(leftTerm)) {
            throw new ExpressionException(String.format("%s is not a number!", leftTerm));
        }
        var leftValue = (IntegerValue) leftTerm;

        var rightTerm = right.evaluate(symbolTable);
        if (isNotInteger(rightTerm)) {
            throw new ExpressionException(String.format("%s is not a number!", rightTerm));
        }
        var rightValue = (IntegerValue) rightTerm;

        int left = leftValue.getValue();
        int right = rightValue.getValue();

        return switch (operator) {
            case "<" -> new BooleanValue(left < right);
            case "<=" -> new BooleanValue(left <= right);
            case "==" -> new BooleanValue(left == right);
            case "!=" -> new BooleanValue(left != right);
            case ">" -> new BooleanValue(left > right);
            case ">=" -> new BooleanValue(left >= right);
            default -> throw new ExpressionException("Unknown relational operator: " + operator);
        };
    }

    private boolean isNotInteger(Value term) {
        return term.getType() != Type.INTEGER;
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left, operator, right);
    }
}