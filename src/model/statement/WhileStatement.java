package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.BooleanValue;
import model.value.Value;

public record WhileStatement(Expression condition, Statement body) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        var stack = programState.executionStack();
        var symTable = programState.symbolTable();
        var heapTable = programState.heapTable();
        Value condValue;

        try {
            condValue = condition.evaluate(symTable, heapTable);
        } catch (ExpressionException e) {
            throw new StatementException("Failed to evaluate WHILE condition: " + condition, e);
        }

        if (!(condValue instanceof BooleanValue(boolean value))) {
            throw new StatementException("WHILE condition is not a boolean: " + condValue);
        }

        if (value) {
            try {
                stack.push(this);
                stack.push(body);
            } catch (AdtException e) {
                throw new StatementException("Failed to push WHILE components onto execution stack", e);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "while(" + condition + ") " + body;
    }
}
