package view;

import controller.Controller;
import model.state.DequeExecutionStack;
import model.state.ListOutput;
import model.state.MapSymbolTable;
import model.state.ProgramState;
import model.statement.Statement;
import repository.MainRepository;
import utilities.KiddoException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class UI {
    private final Map<Integer, String> descriptions = new LinkedHashMap<>();
    private final Map<Integer, Supplier<Statement>> suppliers = new LinkedHashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private Integer selectedId = null;

    public void addProgram(int id, String description, Supplier<Statement> supplier) {
        descriptions.put(id, description);
        suppliers.put(id, supplier);
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print("choice> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            if ("0".equals(line)) {
                System.out.println("Exiting.");
                return;
            } else if ("r".equalsIgnoreCase(line) || "run".equalsIgnoreCase(line)) {
                if (selectedId == null) {
                    System.out.println("No program selected. Choose a program number first.");
                    continue;
                }
                runSelected();
            } else {
                try {
                    int choice = Integer.parseInt(line);
                    if (descriptions.containsKey(choice)) {
                        selectedId = choice;
                        System.out.println("Selected program " + choice + ": " + descriptions.get(choice));
                        System.out.print("Run it now? (y/n) ");
                        String yn = scanner.nextLine().trim();
                        if ("y".equalsIgnoreCase(yn)) runSelected();
                    } else {
                        System.out.println("Unknown program id: " + choice);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a program number, 'r' to run, or 0 to exit.");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("\nAvailable programs:");
        for (Map.Entry<Integer, String> e : descriptions.entrySet()) {
            String mark = (selectedId != null && selectedId.equals(e.getKey())) ? " *" : "";
            System.out.printf("  %d)%s %s%n", e.getKey(), mark, e.getValue());
        }
        System.out.println("\nCommands:");
        System.out.println("  <number>   - select a program (and optionally run it)");
        System.out.println("  r or run   - run the currently selected program");
        System.out.println("  0          - exit");
    }

    private void runSelected() {
        try {
            Supplier<Statement> supplier = suppliers.get(selectedId);
            if (supplier == null) {
                System.out.println("No supplier for selected program.");
                return;
            }
            Statement program = supplier.get();

            DequeExecutionStack stack = new DequeExecutionStack();
            stack.push(program);
            MapSymbolTable symbolTable = new MapSymbolTable();
            ListOutput output = new ListOutput();
            ProgramState state = new ProgramState(stack, symbolTable, output, program);

            Controller ctrl = new Controller(new MainRepository(state));
            System.out.println("----- Running program " + selectedId + " -----");
            ctrl.allStep();
            System.out.println("----- Program finished -----");
            System.out.println(ctrl.getCurrentProgramState());
        } catch (KiddoException ke) {
            System.out.println("Runtime error: " + ke.getMessage());
        } catch (Exception ex) {
            System.out.println("Error during execution: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}