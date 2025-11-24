package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("closeRFile: ProgramState cannot be null");

        Value value;
        try {
            value = expression.evaluate(programState.symbolTable(), programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("closeRFile: failed to evaluate filename expression: " + expression, e);
        }

        if (!(value instanceof StringValue strVal)) {
            throw new StatementException("closeRFile: expression does not evaluate to a string: " + value);
        }

        var fileTable = programState.fileTable();

        try {
            if (!fileTable.isDefined(strVal)) {
                throw new StatementException("closeRFile: file is not opened: " + strVal.value());
            }
        } catch (AdtException e) {
            throw new StatementException("closeRFile: failed checking file table for: " + strVal.value(), e);
        }

        BufferedReader br;
        try {
            br = fileTable.lookup(strVal);
        } catch (AdtException e) {
            throw new StatementException("closeRFile: file not found in table: " + strVal.value(), e);
        }

        try {
            br.close();
        } catch (IOException ioe) {
            throw new StatementException("closeRFile: failed to close \"" + strVal.value() + "\"", ioe);
        }

        try {
            fileTable.remove(strVal);
        } catch (AdtException e) {
            throw new StatementException("closeRFile: failed removing file from table: " + strVal.value(), e);
        }

        return programState;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression + ")";
    }
}
