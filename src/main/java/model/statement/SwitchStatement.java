package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.expression.RelationalExpression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;


public record SwitchStatement(Expression exp, Expression exp1, Statement stmt1,
                              Expression exp2, Statement stmt2, Statement defaultStmt)
        implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        Statement nestedIf = new IfStatement(
                new RelationalExpression(exp, exp1, "=="),
                stmt1,
                new IfStatement(
                        new RelationalExpression(exp, exp2, "=="),
                        stmt2,
                        defaultStmt
                )
        );

        try {
            programState.executionStack().push(nestedIf);
        } catch (AdtException e) {
            throw new StatementException("Failed to push SWITCH transformed statement onto execution stack", e);
        }

        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        Type tExp, tExp1, tExp2;
        try {
            tExp = exp.typecheck(typeEnv);
            tExp1 = exp1.typecheck(typeEnv);
            tExp2 = exp2.typecheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException("Failed to typecheck switch expressions: " + e.getMessage(), e);
        }

        if (!tExp.equals(tExp1) || !tExp.equals(tExp2)) {
            throw new StatementException("Switch expressions must have the same type");
        }

        try {
            stmt1.typecheck(typeEnv.deepCopy());
            stmt2.typecheck(typeEnv.deepCopy());
            defaultStmt.typecheck(typeEnv.deepCopy());
        } catch (AdtException e) {
            throw new StatementException("Error during switch branches typecheck", e);
        }

        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch(" + exp + ") (case " + exp1 + ": " + stmt1 + ") (case " + exp2 + ": " + stmt2 + ") (default: " + defaultStmt + ")";
    }
}
