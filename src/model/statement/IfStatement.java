package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.BooleanValue;
import model.value.Value;

public record IfStatement(Expression condition, Statement thenBranch, Statement elseBranch)
        implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        Value result;

        try {
            result = condition.evaluate(programState.symbolTable(), programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("Failed to evaluate IF condition: " + condition, e);
        }

        if (!(result instanceof BooleanValue(boolean value))) {
            throw new StatementException("IF condition does not evaluate to a boolean: " + result);
        }

        try {
            if (value) programState.executionStack().push(thenBranch);
            else programState.executionStack().push(elseBranch);
        } catch (AdtException e) {
            throw new StatementException("Failed to push IF branch onto execution stack", e);
        }

        return null;
    }

    @Override
    public String toString() {
        return "if(" + condition + ") then(" + thenBranch + ") else(" + elseBranch + ")";
    }
}
