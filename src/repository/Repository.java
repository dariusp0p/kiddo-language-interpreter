package repository;

import model.state.ProgramState;

import java.util.List;

public interface Repository {
    List<ProgramState> getProgramStates();
    ProgramState getCurrentProgramState();
}
