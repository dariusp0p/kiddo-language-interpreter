package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.FileTable;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;
import model.type.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record OpenFileStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("openRFile: ProgramState cannot be null");

        Value value;
        try {
            value = expression.evaluate(programState.symbolTable(), programState.heapTable());
        } catch (ExpressionException e) {
            throw new StatementException("openRFile: failed to evaluate file expression: " + expression, e);
        }

        if (!(value instanceof StringValue strVal)) throw new StatementException("openRFile: expression does not evaluate to a string: " + value);

        String filename = strVal.value();
        FileTable fileTable = programState.fileTable();

        try {
            if (fileTable.isDefined(strVal)) throw new StatementException("openRFile: file is already opened: " + filename);
        } catch (AdtException e) {
            throw new StatementException("openRFile: failed checking file table for: " + filename, e);
        }

        BufferedReader br;
        try {
            br = Files.newBufferedReader(Path.of(filename));
        } catch (IOException ioe) {
            throw new StatementException("openRFile: cannot open file `" + filename + "`", ioe);
        }

        try {
            fileTable.define(strVal, br);
        } catch (AdtException e) {
            throw new StatementException("openRFile: failed to record file in FileTable: " + filename, e);
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        Type typeExp;
        try {
            typeExp = expression.typecheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException("openRFile: failed to evaluate expression: " + expression, e);
        }

        if (!typeExp.equals(new StringType())) {
            throw new StatementException("openRFile: expression is not a string type");
        }

        return typeEnv;
    }
}
