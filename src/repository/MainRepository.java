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

    public MainRepository(ProgramState initialProgram, String logFilePath) {
        this.programStates = new ArrayList<>();
        if (initialProgram != null) {
            this.programStates.add(initialProgram);
        }
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return programStates;
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates.clear();
        this.programStates.addAll(programStates);
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws RepositoryException {
        try (PrintWriter logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true))
        )) {
            logFile.println(programState);
            logFile.println();
        } catch (IOException e) {
            throw new RepositoryException("Failed to write ProgramState to log file: " + logFilePath, e);
        }
    }
}
