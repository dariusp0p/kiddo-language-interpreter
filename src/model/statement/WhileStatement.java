package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.value.Value;
import model.value.BooleanValue;
import utilities.KiddoException;

public record WhileStatement(Expression condition, Statement body) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        var stack = programState.executionStack();
        var symTable = programState.symbolTable();
        var heapTable = programState.heapTable();

        Value condVal = condition.evaluate(symTable, heapTable);

        if (!(condVal instanceof BooleanValue(boolean value))) {
            throw new KiddoException("condition exp is not a boolean");
        }

        if (value) {
            stack.push(this);
            stack.push(body);
        }

        return programState;
    }

    @Override
    public String toString() {
        return "while(" + condition + ") " + body;
    }
}