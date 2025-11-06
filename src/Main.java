import controller.Controller;
import examples.ProgramExamples;
import model.state.*;
import model.statement.Statement;
import repository.MainRepository;
import view.ExitCommand;
import view.RunExampleCommand;
import view.TextMenu;

void main(String[] args) {
    Statement ex1 = ProgramExamples.example1();
    ProgramState prg1 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            ex1
    );
    prg1.executionStack().push(ex1);
    MainRepository repo1 = new MainRepository(prg1, "logs/log1.txt");
    Controller ctr1 = new Controller(repo1);

    Statement ex2 = ProgramExamples.example2();
    ProgramState prg2 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            ex2
    );
    prg2.executionStack().push(ex2);
    MainRepository repo2 = new MainRepository(prg2, "logs/log2.txt");
    Controller ctr2 = new Controller(repo2);

    Statement ex3 = ProgramExamples.example3();
    ProgramState prg3 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            ex3
    );
    prg3.executionStack().push(ex3);
    MainRepository repo3 = new MainRepository(prg3, "logs/log3.txt");
    Controller ctr3 = new Controller(repo3);

    Statement ex4 = ProgramExamples.example4();
    ProgramState prg4 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            ex4
    );
    prg4.executionStack().push(ex4);
    MainRepository repo4 = new MainRepository(prg4, "logs/log4.txt");
    Controller ctr4 = new Controller(repo4);

    TextMenu menu = new TextMenu();
    menu.addCommand(new ExitCommand("0", "exit"));
    menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
    menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
    menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
    menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
    menu.show();
}