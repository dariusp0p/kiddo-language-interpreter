package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.Value;

public record PrintStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        Value value;

        try {
            value = expression.evaluate(programState.symbolTable(),
                    programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("Failed to evaluate print expression: " + expression, e);
        }

        try {
            programState.output().add(value);
        } catch (AdtException e) {
            throw new StatementException("Failed to add value to output", e);
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            expression.typecheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException("Type check failed for print expression: " + expression, e);
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression + ")";
    }
}
