package view;

import controller.Controller;
import utilities.KiddoException;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller ctr) {
        super(key, description);
        this.controller = ctr;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
            System.out.println("Program execution completed.");
            System.out.println(controller.getCurrentProgramState());
        } catch (KiddoException ke) {
            System.out.println("Runtime error: " + ke.getMessage());
        } catch (Exception ex) {
            System.out.println("Error during execution: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}