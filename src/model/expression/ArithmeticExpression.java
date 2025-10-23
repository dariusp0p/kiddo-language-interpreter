package model.expression;

import model.state.SymbolTable;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;
import utilities.KiddoException;

public record ArithmeticExpression
        (Expression left, Expression right, String operator)
        implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable) {
        var leftTerm = left.evaluate(symbolTable);
        if (isNotInteger(leftTerm)) throw new KiddoException(String.format("%s is not a number!", leftTerm));
        var leftValue = (IntegerValue) leftTerm;
        var rightTerm = right.evaluate(symbolTable);
        if (isNotInteger(rightTerm)) throw new KiddoException(String.format("%s is not a number!", rightTerm));
        var rightValue = (IntegerValue) rightTerm;

        if ("/".equals(operator) && rightValue.getValue() == 0) {
            throw new KiddoException("Division by zero!");
        }

        return switch (operator) {
            case "+" -> new IntegerValue(leftValue.getValue() + rightValue.getValue());
            case "-" -> new IntegerValue(leftValue.getValue() - rightValue.getValue());
            case "*" -> new IntegerValue(leftValue.getValue() * rightValue.getValue());
            case "/" -> new IntegerValue(leftValue.getValue() / rightValue.getValue());
            default -> throw new KiddoException("Unknown operator: " + operator);
        };
    }

    private boolean isNotInteger(Value term) {
        return term.getType() == Type.INTEGER;
    }
}
