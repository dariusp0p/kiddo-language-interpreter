package controller;

import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.state.ExecutionStack;
import model.state.GarbageCollector;
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
        repo.logProgramStateExecution();
        while (!programState.executionStack().isEmpty()) {
            oneStep(programState);
            repo.logProgramStateExecution();
            programState.heapTable().setContent(new KiddoHashMapDictionary<>(
                    GarbageCollector.safeGarbageCollector(
                        GarbageCollector.getAddrFromSymTable(programState.symbolTable().getContent().values()),
                        programState.heapTable().getContent()
                    )
            ));
        }
    }

    public ProgramState getCurrentProgramState() {
        return repo.getCurrentProgramState();
    }
}
