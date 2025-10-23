package model.statement;

import model.state.ExecutionStack;
import model.state.ProgramState;

public record CompoundStatement(Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        ExecutionStack stack = programState.executionStack();
        stack.push(second);
        stack.push(first);
        return programState;
    }
}
