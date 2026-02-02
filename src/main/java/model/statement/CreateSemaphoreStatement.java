package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SemaphoreTable;
import model.state.SymbolTable;
import model.type.IntegerType;
import model.value.IntegerValue;
import model.value.Value;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Objects;

public record CreateSemaphoreStatement(String varName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("createSemaphore: ProgramState cannot be null");
        if (varName == null || varName.isBlank()) throw new StatementException("createSemaphore: variable name cannot be null or blank");

        SymbolTable symTable = programState.symbolTable();
        SemaphoreTable semaphoreTable = programState.semaphoreTable();

        // evaluate expression
        Value evaluated;
        try {
            evaluated = expression.evaluate(symTable, programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("createSemaphore: failed to evaluate expression: " + expression, e);
        }

        if (!(evaluated instanceof IntegerValue(int number))) {
            throw new StatementException("createSemaphore: expression does not evaluate to an integer: " + evaluated);
        }

        // allocate new semaphore entry in a synchronized block on the semaphore table
        int newIndex;
        try {
            synchronized (Objects.requireNonNull(semaphoreTable, "SemaphoreTable is null")) {
                newIndex = semaphoreTable.allocate(new AbstractMap.SimpleEntry<>(number, new ArrayList<>()));
            }
        } catch (AdtException e) {
            throw new StatementException("createSemaphore: failed to allocate semaphore entry", e);
        }

        // check variable exists and is int, then update it with the new index
        try {
            if (!symTable.isDefined(varName)) {
                throw new StatementException("createSemaphore: variable \"" + varName + "\" is not defined");
            }
        } catch (AdtException e) {
            throw new StatementException("createSemaphore: failed checking if variable is defined: " + varName, e);
        }

        try {
            if (!symTable.getType(varName).equals(new IntegerType())) {
                throw new StatementException("createSemaphore: variable \"" + varName + "\" is not of type int");
            }
        } catch (AdtException e) {
            throw new StatementException("createSemaphore: failed retrieving type of variable: " + varName, e);
        }

        try {
            symTable.define(varName, new IntegerValue(newIndex));
        } catch (AdtException e) {
            throw new StatementException("createSemaphore: failed to update variable \"" + varName + "\"", e);
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            var tExp = expression.typecheck(typeEnv);
            if (!tExp.equals(new IntegerType())) {
                throw new StatementException("createSemaphore: expression is not of type int");
            }
        } catch (Exception e) {
            throw new StatementException("createSemaphore: failed typechecking expression: " + e.getMessage(), e);
        }

        try {
            var tVar = typeEnv.getType(varName);
            if (!tVar.equals(new IntegerType())) {
                throw new StatementException("createSemaphore: variable " + varName + " is not of type int");
            }
        } catch (AdtException e) {
            throw new StatementException("createSemaphore: variable \"" + varName + "\" is not defined", e);
        }

        return typeEnv;
    }

    @Override
    public String toString() { return "createSemaphore(" + varName + ", " + expression + ")"; }
}
