import controller.Controller;
import examples.ProgramExamples;
import model.expression.ConstantExpression;
import model.state.*;
import model.statement.PrintStatement;
import model.statement.Statement;
import model.value.IntegerValue;
import repository.MainRepository;
import view.UI;

public class Main {
    public static void main(String[] args) {
        Statement prg = new PrintStatement(new ConstantExpression(new IntegerValue(123)));

        DequeExecutionStack stack = new DequeExecutionStack();
        stack.push(prg);
        SymbolTable symbolTable = new MapSymbolTable();
        ListOutput output = new ListOutput();
        ProgramState state = new ProgramState(stack, symbolTable, output, prg);

        Controller ctrl = new Controller(new MainRepository(state));

        UI ui = new UI();
        ui.addProgram(1, "int v; v = 2; Print(v)", () -> ProgramExamples.example1());
        ui.addProgram(2, "int a; int b; a=2+3*5; b=a+1; Print(b)", () -> ProgramExamples.example2());
        ui.addProgram(3, "bool a; int v; a=true; if a then v=2 else v=3; Print(v)", () -> ProgramExamples.example3());

        ui.run();
    }
}
