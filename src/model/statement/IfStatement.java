package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.value.BooleanValue;
import model.value.Value;
import utilities.StatementException;

public record IfStatement (Expression condition, Statement thenBranch, Statement elseBranch) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        Value result = condition.evaluate(programState.symbolTable(), programState.heapTable());
        if (result instanceof BooleanValue booleanValue) {
            if (booleanValue.value()) {
                programState.executionStack().push(thenBranch);
            } else {
                programState.executionStack().push(elseBranch);
            }
        } else {
            throw new StatementException("Condition expression does not evaluate to a boolean!");
        }
        return programState;
    }
}
