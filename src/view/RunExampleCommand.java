package view;

import controller.Controller;
import exceptions.KiddoException;

import java.time.Duration;
import java.time.Instant;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller ctr) {
        super(key, description);
        this.controller = ctr;
    }

    @Override
    public void execute() {
        long startNs = System.nanoTime();
        try {

            controller.allSteps();
            System.out.println("Program execution completed!");
            System.out.println("Output: " + controller.getCurrentProgramState().output());

        } catch (KiddoException ke) {
            System.out.println("Runtime error: " + ke.getMessage());
        } catch (Exception ex) {
            System.out.println("Error during execution: " + ex.getMessage());
            ex.printStackTrace(System.out);
        } finally {
            long endNs = System.nanoTime();
            double millis = (endNs - startNs) / 1_000_000.0;
            System.out.printf("Elapsed time: %.3f ms%n", millis);
            System.out.println();
        }
    }
}
