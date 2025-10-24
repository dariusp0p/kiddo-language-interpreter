package repository;

import model.state.ProgramState;
import utilities.KiddoException;

import java.util.ArrayList;
import java.util.List;

public class MainRepository implements Repository {
    private final List<ProgramState> programStates;

    public MainRepository() {
        this.programStates = new ArrayList<>();
    }

    public MainRepository(ProgramState initialState) {
        this();
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
}
