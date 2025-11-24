package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.util.List;

public interface Repository {
    ProgramState getCurrentProgramState() throws RepositoryException;
    List<ProgramState> getProgramStates();
    void logProgramStateExecution() throws RepositoryException;
}
