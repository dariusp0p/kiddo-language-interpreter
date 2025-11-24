package examples;

import model.expression.ConstantExpression;
import model.expression.RelationalExpression;
import model.expression.VariableExpression;
import model.expression.ArithmeticExpression;
import model.expression.ReadHeapExpression;
import model.statement.*;
import model.type.*;
import model.type.ReferenceType;
import model.value.IntegerValue;
import model.value.BooleanValue;
import model.value.StringValue;

public class ProgramExamples {
    public static Statement example1() {
        // int v;
        // v=2;
        // Print(v)
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "v"),
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
                new VariableDeclarationStatement(new IntegerType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "b"),
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
                new VariableDeclarationStatement(new BooleanType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "v"),
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
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        new AssignmentStatement(new ConstantExpression(new StringValue("./data/test.in")), "varf"),
                        new CompoundStatement(
                                new OpenFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new IntegerType(), "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseFileStatement(new VariableExpression("varf"))
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
                new VariableDeclarationStatement(new IntegerType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "b"),
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
                                                        new VariableDeclarationStatement(new IntegerType(), "c"),
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


    
    
    
    
    public static Statement example6() {
        // Ref int v;
        // new(v,20);
        // print(rH(v));
        // wH(v,30);
        // print(rH(v)+5);
        return new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ConstantExpression(new IntegerValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ConstantExpression(new IntegerValue(5)),
                                                        "+"
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Statement example7() {
        // Ref int v; new(v,20);
        // Ref Ref int a; new(a,v);
        // print(v); print(a);
        // print(rH(v)); print(rH(rH(a))+5);
        return new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntegerType())), "a"),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new CompoundStatement(
                                                                new PrintStatement(
                                                                        new ReadHeapExpression(new VariableExpression("v"))
                                                                ),
                                                                new PrintStatement(
                                                                        new ArithmeticExpression(
                                                                                new ReadHeapExpression(
                                                                                        new ReadHeapExpression(new VariableExpression("a"))
                                                                                ),
                                                                                new ConstantExpression(new IntegerValue(5)),
                                                                                "+"
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Statement example8() {
        // Ref int v; new(v,0);
        // while (rH(v) < 3) {
        //    print(rH(v));
        //    wH(v, rH(v) + 1);
        // }
        // print(rH(v));
        return new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntegerValue(0))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ConstantExpression(new IntegerValue(3)),
                                                "<"
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new HeapWriteStatement(
                                                        "v",
                                                        new ArithmeticExpression(
                                                                new ReadHeapExpression(new VariableExpression("v")),
                                                                new ConstantExpression(new IntegerValue(1)),
                                                                "+"
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))
                        )
                )
        );
    }

    public static Statement example9() {
        // Ref int v; new(v,20);
        // Ref Ref int a; new(a,v);
        // new(v,30);
        // print(rH(rH(a)));

        return new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntegerType())), "a"),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ConstantExpression(new IntegerValue(30))),
                                                new PrintStatement(
                                                        new ReadHeapExpression(
                                                                new ReadHeapExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Statement example10() {
        // Ref Ref Ref int r;
        // int v; v = 99;
        // Ref int p; new(p, v);
        // Ref Ref int q; new(q, p);
        // new(r, q);
        // print(rH(rH(rH(r))));
        //
        // Expected: 99

        return new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new ReferenceType(new IntegerType()))), "r"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "v"),
                        new CompoundStatement(
                                new AssignmentStatement(new ConstantExpression(new IntegerValue(99)), "v"),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "p"),
                                        new CompoundStatement(
                                                new NewStatement("p", new VariableExpression("v")),
                                                new CompoundStatement(
                                                        new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntegerType())), "q"),
                                                        new CompoundStatement(
                                                                new NewStatement("q", new VariableExpression("p")),
                                                                new CompoundStatement(
                                                                        new NewStatement("r", new VariableExpression("q")),
                                                                        new PrintStatement(
                                                                                new ReadHeapExpression(
                                                                                        new ReadHeapExpression(
                                                                                                new ReadHeapExpression(new VariableExpression("r"))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Statement example11() {
        // Tests SAFE GC (should NOT delete reachable cells):
        //
        // Ref int v; new(v,1);
        // Ref Ref int a; new(a,v);
        // int x; x = 0;
        //
        // while (x < 2) {
        //     new(v, x);      // old cells become unreachable except through a
        //     x = x + 1;
        // }
        //
        // print(rH(rH(a)));
        //
        // Expected: value from the LAST reachable v *through a*, not the overwritten versions.

        return new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(new IntegerType()), "v"),
                new CompoundStatement(
                        new NewStatement("v", new ConstantExpression(new IntegerValue(1))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(new IntegerType())), "a"),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement(new IntegerType(), "x"),
                                                new CompoundStatement(
                                                        new AssignmentStatement(new ConstantExpression(new IntegerValue(0)), "x"),
                                                        new CompoundStatement(
                                                                new WhileStatement(
                                                                        new RelationalExpression(
                                                                                new VariableExpression("x"),
                                                                                new ConstantExpression(new IntegerValue(2)),
                                                                                "<"
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new NewStatement("v", new VariableExpression("x")),
                                                                                new AssignmentStatement(
                                                                                        new ArithmeticExpression(
                                                                                                new VariableExpression("x"),
                                                                                                new ConstantExpression(new IntegerValue(1)),
                                                                                                "+"
                                                                                        ),
                                                                                        "x"
                                                                                )
                                                                        )
                                                                ),
                                                                new PrintStatement(
                                                                        new ReadHeapExpression(
                                                                                new ReadHeapExpression(new VariableExpression("a"))
                                                                        )
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