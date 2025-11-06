package repository;

import model.state.ProgramState;
import utilities.KiddoException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainRepository implements Repository {
    private final List<ProgramState> programStates;
    private final String logFilePath;

    public MainRepository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    public MainRepository(ProgramState initialState,  String logFilePath) {
        this(logFilePath);
        if (initialState != null) this.programStates.add(initialState);
    }

    @Override
    public ProgramState getCurrentProgramState() {
        if (programStates.isEmpty()) throw new KiddoException("No program states in repository!");
        return programStates.getLast();
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return programStates;
    }

    public void addProgramState(ProgramState state) {
        if (state == null) throw new KiddoException("State cannot be null!");
        programStates.add(state);
    }

    @Override
    public void logProgramStateExecution() {
        ProgramState state = getCurrentProgramState();
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(state.toString());
            logFile.println();
            logFile.flush();
        } catch (IOException _) {
            throw new KiddoException("Failed to log ProgramState to file: " + logFilePath);
        }
    }
}
