package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.state.SymbolTable;

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
        return null;
    }

    // TODO - understand
    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}
