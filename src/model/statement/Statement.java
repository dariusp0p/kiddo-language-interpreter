package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState programState) throws StatementException, AdtException;
}
