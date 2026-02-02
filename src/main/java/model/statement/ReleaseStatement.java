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

public record ReleaseStatement(String varName) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, AdtException {
        if (programState == null) throw new StatementException("release: ProgramState cannot be null");
        if (varName == null || varName.isBlank()) throw new StatementException("release: variable name cannot be null or blank");

        SymbolTable symTable = programState.symbolTable();
        Value value;
        try {
            value = symTable.lookup(varName);
        } catch (AdtException e) {
            throw new StatementException("release: variable \"" + varName + "\" is not defined", e);
        }

        if (!(value instanceof IntegerValue)) {
            throw new StatementException("release: variable \"" + varName + "\" is not an integer");
        }
        int foundIndex = ((IntegerValue) value).value();

        SemaphoreTable semaphoreTable = programState.semaphoreTable();
        if (semaphoreTable == null) throw new StatementException("release: SemaphoreTable is null");

        try {
            synchronized (Objects.requireNonNull(semaphoreTable)) {
                if (!semaphoreTable.contains(foundIndex)) {
                    throw new StatementException("release: semaphore index " + foundIndex + " not found");
                }

                AbstractMap.SimpleEntry<Integer, List<Integer>> entry = semaphoreTable.get(foundIndex);
                int n1 = entry.getKey();
                List<Integer> owners = entry.getValue() == null ? new ArrayList<>() : new ArrayList<>(entry.getValue());
                int myId = programState.id();

                if (owners.contains(myId)) {
                    owners.remove(Integer.valueOf(myId));
                    semaphoreTable.update(foundIndex, new AbstractMap.SimpleEntry<>(n1, owners));
                }
                // else: do nothing
            }
        } catch (AdtException e) {
            throw e;
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            if (!typeEnv.getType(varName).equals(new IntegerType())) {
                throw new StatementException("release: variable " + varName + " is not of type int");
            }
        } catch (AdtException e) {
            throw new StatementException("release: variable \"" + varName + "\" is not defined", e);
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "release(" + varName + ")";
    }
}
