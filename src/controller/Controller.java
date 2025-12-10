package controller;

import exceptions.KiddoException;
import exceptions.RepositoryException;
import model.adt.KiddoHashMapDictionary;
import model.state.GarbageCollector;
import model.state.ProgramState;
import model.value.Value;
import repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final Repository repo;
    private ExecutorService executor;

    public Controller(Repository repo) {
        this.repo = repo;
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) throws KiddoException {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrograms(List<ProgramState> programStates) throws KiddoException {
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) p::oneStep)
                .collect(Collectors.toList());

        List<ProgramState> newProgramStates;
        try {
            newProgramStates = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException _) {
                            Thread.currentThread().interrupt();
                            return null;
                        } catch (ExecutionException e) {
                            throw new RuntimeException("Error while executing program step", e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KiddoException("Executor interrupted while executing programs", e);
        }

        programStates.addAll(newProgramStates);

        Set<Integer> rootAddresses = programStates.stream()
                .flatMap(prg ->
                        GarbageCollector.getAddrFromSymTable(
                                prg.symbolTable().getContent().values()
                        ).stream()
                )
                .collect(Collectors.toSet());

        if (!programStates.isEmpty()) {
            Map<Integer, Value> newHeapContent = GarbageCollector.safeGarbageCollector(
                    rootAddresses,
                    programStates.getFirst().heapTable().getContent()
            );

            for (ProgramState prg : programStates) {
                prg.heapTable().setContent(new KiddoHashMapDictionary<>(newHeapContent));
            }
        }

        for (ProgramState prg : programStates) {
            try {
                repo.logProgramStateExecution(prg);
            } catch (RepositoryException e) {
                throw new KiddoException("Failed to log ProgramState after step", e);
            }
        }

        repo.setProgramStates(programStates);
    }

    public void allSteps() throws KiddoException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(getProgramListFromRepo());

        while (!programStates.isEmpty()) {
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrograms(getProgramListFromRepo());
        }

        executor.shutdownNow();
    }

    private List<ProgramState> getProgramListFromRepo() {
        return repo.getProgramStates();
    }

    public ProgramState getCurrentProgramState() throws KiddoException {
        List<ProgramState> programStates = getProgramListFromRepo();
        if (programStates.isEmpty()) {
            throw new KiddoException("No ProgramState in repository");
        }
        return programStates.getFirst();
    }
}
