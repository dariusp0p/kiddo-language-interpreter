package examples;

import model.expression.ConstantExpression;
import model.expression.RelationalExpression;
import model.expression.VariableExpression;
import model.expression.ArithmeticExpression;
import model.statement.*;
import model.type.Type;
import model.value.IntegerValue;
import model.value.BooleanValue;
import model.value.StringValue;

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

    public static Statement example4() {
        // string varf;
        // varf="test.in";
        // openRFile(varf);
        // int varc;
        // readFile(varf,varc); print(varc);
        // readFile(varf,varc); print(varc);
        // closeRFile(varf);
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.STRING, "varf"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new StringValue("./data/test.in")), "varf"),
                        new CompoundStatement(
                                new OpenReadFile(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(Type.INTEGER, "varc"),
                                        new CompoundStatement(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Statement example5() {
        // int a; int b;
        // a = 10;
        // b = 5;
        // if (a > b) then Print(a) else Print(b);
        // int c;
        // c = a - b;
        // if (c >= 5) then Print(100) else Print(200);
        return new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "b"),
                        new CompoundStatement(
                                new AssignmentStatement(new ConstantExpression(new IntegerValue(10)), "a"),
                                new CompoundStatement(
                                        new AssignmentStatement(new ConstantExpression(new IntegerValue(5)), "b"),
                                        new CompoundStatement(
                                                new IfStatement(
                                                        new RelationalExpression(
                                                                new VariableExpression("a"),
                                                                new VariableExpression("b"),
                                                                ">"
                                                        ),
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new PrintStatement(new VariableExpression("b"))
                                                ),
                                                new CompoundStatement(
                                                        new VariableDeclarationStatement(Type.INTEGER, "c"),
                                                        new CompoundStatement(
                                                                new AssignmentStatement(
                                                                        new ArithmeticExpression(
                                                                                new VariableExpression("a"),
                                                                                new VariableExpression("b"),
                                                                                "-"
                                                                        ),
                                                                        "c"
                                                                ),
                                                                new IfStatement(
                                                                        new RelationalExpression(
                                                                                new VariableExpression("c"),
                                                                                new ConstantExpression(new IntegerValue(5)),
                                                                                ">="
                                                                        ),
                                                                        new PrintStatement(new ConstantExpression(new IntegerValue(100))),
                                                                        new PrintStatement(new ConstantExpression(new IntegerValue(200)))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }
}