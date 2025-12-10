package model.statement;

import exceptions.AdtException;
import exceptions.StatementException;
import model.state.ProgramState;
import model.state.SymbolTable;

public interface Statement {
    ProgramState execute(ProgramState programState) throws StatementException, AdtException;
    SymbolTable typecheck(SymbolTable typeEnv) throws StatementException;
}
