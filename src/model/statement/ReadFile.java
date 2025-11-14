package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.type.Type;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;
import utilities.KiddoException;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFile(Expression expression, String variableName) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        if (programState == null) throw new KiddoException("readFile: ProgramState cannot be null!");
        if (variableName == null || variableName.isBlank()) throw new KiddoException("readFile: invalid variable name!");

        var symTable = programState.symbolTable();
        if (!symTable.isDefined(variableName)) throw new KiddoException("readFile: variable \"" + variableName + "\" is not defined!");
        if (symTable.getType(variableName) != Type.INTEGER) throw new KiddoException("readFile: variable \"" + variableName + "\" must be of type int!");

        Value value = expression.evaluate(symTable);
        if (!(value instanceof StringValue strVal)) throw new KiddoException("readFile: expression must evaluate to a string!");
        BufferedReader br = programState.fileTable().lookup(strVal);

        String line;
        try {
            line = br.readLine();
        } catch (IOException ioe) {
            throw new KiddoException("readFile: failed to read from \"" + strVal.getValue() + "\": " + ioe.getMessage());
        }

        int number;
        if (line == null) {
            number = 0;
        } else {
            try {
                number = Integer.parseInt(line.trim());
            } catch (NumberFormatException _) {
                throw new KiddoException("readFile: content is not an integer: \"" + line + "\"");
            }
        }
        symTable.update(variableName, new IntegerValue(number));
        return programState;
    }
}