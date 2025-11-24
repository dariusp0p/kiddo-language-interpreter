package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.value.Value;

public record PrintStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        Value value = expression.evaluate(programState.symbolTable(),  programState.heapTable());
        programState.output().add(value);
        return programState;
    }

    @Override
    public String toString() { return "print(" + expression + ")"; }
}
