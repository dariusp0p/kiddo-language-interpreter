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

    Statement ex5 = ProgramExamples.example5();
    ProgramState prg5 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            ex5
    );
    prg5.executionStack().push(ex5);
    MainRepository repo5 = new MainRepository(prg5, "logs/log5.txt");
    Controller ctr5 = new Controller(repo5);

    TextMenu menu = new TextMenu();
    menu.addCommand(new ExitCommand("0", "exit"));
    menu.addCommand(new RunExampleCommand("1", "int v; v=2; Print(v)", ctr1));    menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
    menu.addCommand(new RunExampleCommand("2", "int a; int b; a = 2 + 3 * 5; b = a + 1; Print(b)", ctr2));
    menu.addCommand(new RunExampleCommand("3", "bool a; int v; a = true; if a then v = 2 else v = 3; Print(v)", ctr3));
    menu.addCommand(new RunExampleCommand("4", "string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)", ctr4));
    menu.addCommand(new RunExampleCommand("5", "int a; int b; a = 10; b = 5; if (a > b) then Print(a) else Print(b); int c; c = a - b; if (c >= 5) then Print(100) else Print(200)", ctr5));
    menu.show();
}