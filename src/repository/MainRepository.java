package repository;

import exceptions.RepositoryException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainRepository implements Repository {
    private final List<ProgramState> programStates;
    private final String logFilePath;

    public MainRepository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    public MainRepository(ProgramState initialState, String logFilePath) {
        this(logFilePath);
        if (initialState != null) this.programStates.add(initialState);
    }

    @Override
    public ProgramState getCurrentProgramState() throws RepositoryException {
        if (programStates.isEmpty()) throw new RepositoryException("Repository contains no program states.");
        return programStates.getLast();
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return programStates;
    }

    public void addProgramState(ProgramState state) throws RepositoryException {
        if (state == null) throw new RepositoryException("Cannot add null ProgramState to repository.");
        programStates.add(state);
    }

    @Override
    public void logProgramStateExecution() throws RepositoryException {
        ProgramState state = getCurrentProgramState();

        try (PrintWriter logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true))
        )) {
            logFile.println(state);
            logFile.println();
        } catch (IOException e) {
            throw new RepositoryException("Failed to write ProgramState to log file: " + logFilePath, e);
        }
    }
}
