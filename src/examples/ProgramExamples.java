package examples;

import model.expression.ConstantExpression;
import model.expression.VariableExpression;
import model.expression.ArithmeticExpression;
import model.statement.*;
import model.type.Type;
import model.value.IntegerValue;
import model.value.BooleanValue;

public class ProgramExamples {
    public static Statement example1() {
        // int v;
        // v=2;
        // Print(v)
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new IntegerValue(2)), "v"),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    public static Statement example2() {
        // int a; int b;
        // a = 2 + 3 * 5;
        // b = a + 1;
        // Print(b)
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "b"),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        new ArithmeticExpression(
                                                new ConstantExpression(new IntegerValue(2)),
                                                new ArithmeticExpression(
                                                        new ConstantExpression(new IntegerValue(3)),
                                                        new ConstantExpression(new IntegerValue(5)),
                                                        "*"
                                                ),
                                                "+"
                                        ),
                                        "a"
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ConstantExpression(new IntegerValue(1)),
                                                        "+"
                                                ),
                                                "b"
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }

    public static Statement example3() {
        // bool a; int v;
        // a = true;
        // if a then v = 2 else v = 3;
        // Print(v)
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.BOOLEAN, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "v"),
                        new CompoundStatement(
                                new AssignmentStatement(new ConstantExpression(new BooleanValue(true)), "a"),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(2)), "v"),
                                                new AssignmentStatement(new ConstantExpression(new IntegerValue(3)), "v")
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }
}