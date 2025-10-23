package model.statement;

import model.state.ProgramState;

public record BlankStatement() implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) { return programState; }
}
