package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.ProgramState;
import model.state.SemaphoreTable;
import model.state.SymbolTable;
import model.type.IntegerType;
import model.value.IntegerValue;
import model.value.Value;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record AcquireStatement(String varName) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("acquire: ProgramState cannot be null");
        if (varName == null || varName.isBlank()) throw new StatementException("acquire: variable name cannot be null or blank");

        SymbolTable symTable = programState.symbolTable();
        Value value;
        try {
            value = symTable.lookup(varName);
        } catch (AdtException e) {
            throw new StatementException("acquire: failed to read variable \"" + varName + "\"", e);
        }

        if (!(value instanceof IntegerValue)) {
            throw new StatementException("acquire: variable \"" + varName + "\" is not an integer");
        }
        int foundIndex = ((IntegerValue) value).value();

        SemaphoreTable semaphoreTable = programState.semaphoreTable();
        if (semaphoreTable == null) throw new StatementException("acquire: SemaphoreTable is null");

        try {
            synchronized (Objects.requireNonNull(semaphoreTable)) {
                if (!semaphoreTable.contains(foundIndex)) {
                    throw new StatementException("acquire: semaphore index " + foundIndex + " not found");
                }

                AbstractMap.SimpleEntry<Integer, List<Integer>> entry = semaphoreTable.get(foundIndex);
                int n1 = entry.getKey();
                List<Integer> owners = entry.getValue() == null ? new ArrayList<>() : new ArrayList<>(entry.getValue());
                int nl = owners.size();
                int myId = programState.id();

                if (n1 > nl) {
                    if (!owners.contains(myId)) {
                        owners.add(myId);
                        semaphoreTable.update(foundIndex, new AbstractMap.SimpleEntry<>(n1, owners));
                    }
                    // acquired successfully (do nothing else)
                } else {
                    // no permit available, retry later
                    programState.executionStack().push(this);
                }
            }
        } catch (AdtException e) {
            throw new StatementException("acquire: semaphore operation failed", e);
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            var tVar = typeEnv.getType(varName);
            if (!tVar.equals(new IntegerType())) {
                throw new StatementException("acquire: variable " + varName + " is not of type int");
            }
        } catch (AdtException e) {
            throw new StatementException("acquire: variable \"" + varName + "\" is not defined", e);
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "acquire(" + varName + ")";
    }
}
