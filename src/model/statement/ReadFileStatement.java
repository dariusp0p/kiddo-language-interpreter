package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.IntegerType;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public record ReadFileStatement(Expression expression, String variableName) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("readFile: ProgramState cannot be null");
        if (variableName == null || variableName.isBlank()) throw new StatementException("readFile: invalid variable name");

        var symTable = programState.symbolTable();

        try {
            if (!symTable.isDefined(variableName)) throw new StatementException("readFile: variable \"" + variableName + "\" is not defined");
        } catch (AdtException e) {
            throw new StatementException("readFile: failed checking if variable is defined: " + variableName, e);
        }

        try {
            if (!Objects.equals(symTable.getType(variableName), new IntegerType())) {
                throw new StatementException("readFile: variable \"" + variableName + "\" must be of type int");
            }
        } catch (AdtException e) {
            throw new StatementException("readFile: failed retrieving type of variable \"" + variableName + "\"", e);
        }

        Value value;
        try {
            value = expression.evaluate(symTable, programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("readFile: failed to evaluate filename expression: " + expression, e);
        }

        if (!(value instanceof StringValue strVal)) {
            throw new StatementException("readFile: expression does not evaluate to a string: " + value);
        }

        BufferedReader br;
        try {
            br = programState.fileTable().lookup(strVal);
        } catch (AdtException e) {
            throw new StatementException("readFile: file is not opened: " + strVal.value(), e);
        }

        String line;
        try {
            line = br.readLine();
        } catch (IOException ioe) {
            throw new StatementException("readFile: failed reading from \"" + strVal.value() + "\"", ioe);
        }

        int number;
        if (line == null) {
            number = 0;
        } else {
            try {
                number = Integer.parseInt(line.trim());
            } catch (NumberFormatException _) {
                throw new StatementException("readFile: file content is not an integer: \"" + line + "\"");
            }
        }

        try {
            symTable.define(variableName, new IntegerValue(number));
        } catch (AdtException e) {
            throw new StatementException("readFile: failed updating variable \"" + variableName + "\"", e);
        }

        return programState;
    }

    @Override
    public String toString() {
        return "readFile(" + expression + ", " + variableName + ")";
    }
}
