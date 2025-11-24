package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.ExecutionStack;
import model.state.ProgramState;

public record CompoundStatement(Statement first, Statement second) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        ExecutionStack executionStack = programState.executionStack();

        try {
            executionStack.push(second);
            executionStack.push(first);
        } catch (AdtException e) {
            throw new StatementException("Failed to push statements onto execution executionStack", e);
        }
        return programState;
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}
