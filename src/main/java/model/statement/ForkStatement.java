package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.*;

public record ForkStatement (Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, AdtException {
        ExecutionStack newExecutionStack = new DequeExecutionStack();
        newExecutionStack.push(statement);
        SymbolTable newSymbolTable = programState.symbolTable().deepCopy();
        return new ProgramState(
                newExecutionStack,
                newSymbolTable,
                programState.output(),
                programState.fileTable(),
                programState.heapTable()
        );
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            statement.typecheck(typeEnv.deepCopy());
        } catch (AdtException e) {
            throw new StatementException(e.getMessage(), e);
        }
        return typeEnv;
    }
}
