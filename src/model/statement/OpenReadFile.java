package model.statement;

import model.expression.Expression;
import model.state.FileTable;
import model.state.ProgramState;
import model.value.StringValue;
import model.value.Value;
import utilities.KiddoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record OpenReadFile(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        if (programState == null) throw new KiddoException("openRFile: ProgramState cannot be null!");

        Value value = expression.evaluate(programState.symbolTable(), programState.heapTable());

        if (!(value instanceof StringValue strVal)) throw new KiddoException("openRFile: expression must evaluate to a string!");

        FileTable fileTable = programState.fileTable();
        if (fileTable.isDefined(strVal)) throw new KiddoException("openRFile: file already opened: " + strVal);

        String path = strVal.value();
        try {
            BufferedReader br = Files.newBufferedReader(Path.of(path));
            fileTable.define(strVal, br);
        } catch (IOException ioe) {
            throw new KiddoException("openRFile: failed to open `" + path + "`: " + ioe.getMessage());
        }

        return programState;
    }
}
