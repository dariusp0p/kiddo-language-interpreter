package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.util.List;

public interface Repository {
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);
    void logProgramStateExecution(ProgramState programState) throws RepositoryException;
}
