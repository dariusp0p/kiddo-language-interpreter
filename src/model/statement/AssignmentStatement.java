package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;
import model.value.Value;

public record AssignmentStatement(Expression expression, String variableName) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        SymbolTable symbolTable = programState.symbolTable();

        try {
            if (!symbolTable.isDefined(variableName)) throw new StatementException("Variable \"" + variableName + "\" is not defined");
        } catch (AdtException e) {
            throw new StatementException("Failed checking if variable is defined: " + variableName, e);
        }

        Value value;
        try {
            value = expression.evaluate(symbolTable, programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("Failed to evaluate assignment expression for" + variableName + ": " + expression, e);
        }

        Type declaredType;
        try {
            declaredType = symbolTable.getType(variableName);
        } catch (AdtException e) {
            throw new StatementException("Failed to read declared type of variable: " + variableName, e);
        }

        if (!value.getType().equals(declaredType)) {
            throw new StatementException("Type mismatch: variable \"" + variableName + "\" has type " +
                            declaredType + " but expression evaluated to " + value.getType()
            );
        }

        try {
            symbolTable.define(variableName, value);
        } catch (AdtException e) {
            throw new StatementException("Failed to update variable \"" + variableName + "\"", e);
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        Type typeVar;
        try {
            typeVar = typeEnv.getType(variableName);
        } catch (AdtException e) {
            throw new StatementException("Assignment: variable \"" + variableName + "\" is not defined", e);
        }

        try {
            Type typeExp = expression.typecheck(typeEnv);
            if (!typeVar.equals(typeExp)) {
                throw new StatementException("Assignment: right hand side and left hand side have different types");
            }
        } catch (ExpressionException e) {
            throw new StatementException("Failed to evaluate assignment expression for " + variableName, e);
        }

        return typeEnv;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression;
    }
}
