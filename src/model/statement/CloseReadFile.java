package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.value.StringValue;
import model.value.Value;
import utilities.KiddoException;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseReadFile(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        if (programState == null) throw new KiddoException("closeRFile: ProgramState cannot be null!");

        Value value = expression.evaluate(programState.symbolTable(),  programState.heapTable());
        if (!(value instanceof StringValue strVal)) {
            throw new KiddoException("closeRFile: expression must evaluate to a string!");
        }

        var fileTable = programState.fileTable();
        if (!fileTable.isDefined(strVal)) {
            throw new KiddoException("closeRFile: file is not opened: " + strVal);
        }

        BufferedReader br = fileTable.lookup(strVal);
        try {
            br.close();
        } catch (IOException ioe) {
            throw new KiddoException("closeRFile: failed to close \"" + strVal.value() + "\": " + ioe.getMessage());
        }

        fileTable.remove(strVal);
        return programState;
    }
}
