package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.LockTable;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.IntegerValue;
import model.value.Value;
import model.type.IntegerType;

public record UnlockStatement(String varName) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, AdtException {
        SymbolTable symTable = state.symbolTable();
        if (!symTable.isDefined(varName))
            throw new StatementException("Variable " + varName + " is not defined.");

        Value v = symTable.lookup(varName);
        if (!(v instanceof IntegerValue))
            throw new StatementException("Variable " + varName + " is not of type int.");

        int foundIndex = ((IntegerValue) v).value();

        LockTable lockTable = state.lockTable();
        lockTable.release(foundIndex, state.getId());

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            if (!typeEnv.isDefined(varName))
                throw new StatementException("Variable " + varName + " is not declared in type environment.");
            Value v = typeEnv.lookup(varName);
            if (!(v.getType() instanceof IntegerType))
                throw new StatementException("Variable " + varName + " is not of type int.");
            return typeEnv;
        } catch (AdtException e) {
            throw new StatementException(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "unlock(" + varName + ")";
    }
}
