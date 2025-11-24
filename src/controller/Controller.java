package controller;

import exceptions.KiddoException;
import exceptions.RepositoryException;
import model.adt.KiddoHashMapDictionary;
import model.state.ExecutionStack;
import model.state.GarbageCollector;
import model.state.ProgramState;
import model.statement.Statement;
import repository.Repository;

import java.util.Map;
import java.util.Set;

public class Controller {
    private final Repository repo;

    public Controller(Repository repo) {
        this.repo = repo;
    }

    private ProgramState oneStep(ProgramState state) throws KiddoException {
        ExecutionStack stack = state.executionStack();

        if (stack.isEmpty())
            throw new KiddoException("Program stack is empty!");

        Statement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allSteps() throws KiddoException {
        ProgramState state;

        try {
            state = repo.getCurrentProgramState();
        } catch (RepositoryException e) {
            throw new KiddoException("Failed to get current program state", e);
        }

        try {
            repo.logProgramStateExecution();
        } catch (RepositoryException e) {
            throw new KiddoException("Failed to log initial ProgramState", e);
        }

        while (!state.executionStack().isEmpty()) {
            oneStep(state);

            try {
                repo.logProgramStateExecution();
            } catch (RepositoryException e) {
                throw new KiddoException("Failed to log ProgramState after step", e);
            }

            Set<Integer> roots = GarbageCollector.getAddrFromSymTable(
                    state.symbolTable().getContent().values());
            Map<Integer, model.value.Value> newHeapContent = GarbageCollector.safeGarbageCollector(
                    roots,
                    state.heapTable().getContent()
            );
            state.heapTable().setContent(new KiddoHashMapDictionary<>(newHeapContent));
        }
    }

    public ProgramState getCurrentProgramState() throws KiddoException {
        try {
            return repo.getCurrentProgramState();
        } catch (RepositoryException e) {
            throw new KiddoException("Failed to get current ProgramState from repository", e);
        }
    }
}
