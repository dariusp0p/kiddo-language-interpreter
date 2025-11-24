import controller.Controller;
import examples.ProgramExamples;
import model.state.*;
import model.statement.Statement;
import repository.MainRepository;
import view.ExitCommand;
import view.RunExampleCommand;
import view.TextMenu;

void main(String[] args) {

    // Example 1
    Statement ex1 = ProgramExamples.example1();
    ProgramState prg1 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg1.executionStack().push(ex1);
    Controller ctr1 = new Controller(new MainRepository(prg1, "logs/log1.txt"));

    // Example 2
    Statement ex2 = ProgramExamples.example2();
    ProgramState prg2 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg2.executionStack().push(ex2);
    Controller ctr2 = new Controller(new MainRepository(prg2, "logs/log2.txt"));

    // Example 3
    Statement ex3 = ProgramExamples.example3();
    ProgramState prg3 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg3.executionStack().push(ex3);
    Controller ctr3 = new Controller(new MainRepository(prg3, "logs/log3.txt"));

    // Example 4
    Statement ex4 = ProgramExamples.example4();
    ProgramState prg4 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg4.executionStack().push(ex4);
    Controller ctr4 = new Controller(new MainRepository(prg4, "logs/log4.txt"));

    // Example 5
    Statement ex5 = ProgramExamples.example5();
    ProgramState prg5 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg5.executionStack().push(ex5);
    Controller ctr5 = new Controller(new MainRepository(prg5, "logs/log5.txt"));

    // Example 6
    Statement ex6 = ProgramExamples.example6();
    ProgramState prg6 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg6.executionStack().push(ex6);
    Controller ctr6 = new Controller(new MainRepository(prg6, "logs/log6.txt"));

    // Example 7
    Statement ex7 = ProgramExamples.example7();
    ProgramState prg7 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg7.executionStack().push(ex7);
    Controller ctr7 = new Controller(new MainRepository(prg7, "logs/log7.txt"));

    // Example 8
    Statement ex8 = ProgramExamples.example8();
    ProgramState prg8 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg8.executionStack().push(ex8);
    Controller ctr8 = new Controller(new MainRepository(prg8, "logs/log8.txt"));

    // Example 9
    Statement ex9 = ProgramExamples.example9();
    ProgramState prg9 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg9.executionStack().push(ex9);
    Controller ctr9 = new Controller(new MainRepository(prg9, "logs/log9.txt"));

    // Example 10
    Statement ex10 = ProgramExamples.example10();
    ProgramState prg10 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg10.executionStack().push(ex10);
    Controller ctr10 = new Controller(new MainRepository(prg10, "logs/log10.txt"));

    // Example 11
    Statement ex11 = ProgramExamples.example11();
    ProgramState prg11 = new ProgramState(
            new DequeExecutionStack(),
            new MapSymbolTable(),
            new ListOutput(),
            new MapFileTable(),
            new MapHeapTable()
    );
    prg11.executionStack().push(ex11);
    Controller ctr11 = new Controller(new MainRepository(prg11, "logs/log11.txt"));

    TextMenu menu = new TextMenu();
    menu.addCommand(new ExitCommand("0", "exit"));

    menu.addCommand(new RunExampleCommand("1", "int v; v = 2; Print(v)", ctr1));
    menu.addCommand(new RunExampleCommand("2", "int a; int b; a = 2 + 3 * 5; b = a + 1; Print(b)", ctr2));
    menu.addCommand(new RunExampleCommand("3", "bool a; int v; a = true; if a then v = 2 else v = 3; Print(v)", ctr3));
    menu.addCommand(new RunExampleCommand("4", "string varf; varf = \"./data/test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)", ctr4));
    menu.addCommand(new RunExampleCommand("5", "int a; int b; a = 10; b = 5; if (a > b) then Print(a) else Print(b); int c = a - b; if (c >= 5) then Print(100) else Print(200)", ctr5));
    menu.addCommand(new RunExampleCommand("6", "Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v) + 5)", ctr6));
    menu.addCommand(new RunExampleCommand("7", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a); print(rH(v)); print(rH(rH(a)) + 5)", ctr7));
    menu.addCommand(new RunExampleCommand("8", "Ref int v; new(v,0); while (rH(v) < 3) { print(rH(v)); wH(v, rH(v) + 1); } print(rH(v))", ctr8));
    menu.addCommand(new RunExampleCommand("9", "Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))", ctr9));
    menu.addCommand(new RunExampleCommand("10", "Ref Ref Ref int r; int v; v = 99; Ref int p; new(p,v); Ref Ref int q; new(q,p); new(r,q); print(rH(rH(rH(r)))) (expected 99)", ctr10));
    menu.addCommand(new RunExampleCommand("11", "SAFE GC test: Ref int v; new(v,1); Ref Ref int a; new(a,v); loop new(v,x) twice; print(rH(rH(a)))", ctr11));


    menu.show();
}
