package model.expression;

import model.state.HeapTable;
import model.state.SymbolTable;
import model.type.IntegerType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;
import utilities.ExpressionException;

public record RelationalExpression
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

        int left = leftValue.value();
        int right = rightValue.value();

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
        return !(term.getType() instanceof IntegerType);
    }
}
