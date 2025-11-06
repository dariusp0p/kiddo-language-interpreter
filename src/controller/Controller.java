package controller;

import model.state.ExecutionStack;
import model.state.ProgramState;
import model.statement.Statement;
import repository.Repository;
import utilities.KiddoException;

public class Controller {
    private final Repository repo;
    public Controller(Repository r) { this.repo = r; }

    private ProgramState oneStep(ProgramState state) {
        ExecutionStack stack = state.executionStack();
        if (stack.isEmpty()) throw new KiddoException("Program stack is empty!");
        Statement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() {
        ProgramState programState = repo.getCurrentProgramState();
        while (!programState.executionStack().isEmpty()) {
            ProgramState currentState = oneStep(programState);
            System.out.println(currentState.toString());
        }
    }

    public String getCurrentProgramState() {
        ProgramState programState = repo.getCurrentProgramState();
        return programState.toString();
    }
}
