package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.expression.VariableExpression;
import model.expression.RelationalExpression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.Type;
import model.type.IntegerType;


public record ForStatement(String varName, Expression exp1, Expression exp2, Expression exp3, Statement body) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, AdtException {
        var stack = programState.executionStack();

        Statement decl = new VariableDeclarationStatement(new IntegerType(), varName);
        Statement init = new AssignmentStatement(exp1, varName);

        Expression condition = new RelationalExpression(new VariableExpression(varName), exp2, "<");
        Statement incr = new AssignmentStatement(exp3, varName);
        Statement whileBody = new CompoundStatement(body, incr);
        Statement whileStmt = new WhileStatement(condition, whileBody);

        Statement desugared = new CompoundStatement(decl, new CompoundStatement(init, whileStmt));

        stack.push(desugared);
        return null;
    }

    @Override
    public SymbolTable typecheck(SymbolTable typeEnv) throws StatementException {
        try {
            var env2 = typeEnv.deepCopy();
            env2.define(varName, new IntegerType().getDefaultValue());

            Type t1 = exp1.typecheck(env2);
            Type t2 = exp2.typecheck(env2);
            Type t3 = exp3.typecheck(env2);

            if (!t1.equals(new IntegerType()) || !t2.equals(new IntegerType()) || !t3.equals(new IntegerType())) {
                throw new StatementException("FOR: all expressions (exp1, exp2, exp3) must be of type int");
            }

            body.typecheck(env2);
            return typeEnv;
        } catch (ExpressionException e) {
            throw new StatementException("Failed to typecheck for-expressions", e);
        } catch (AdtException e) {
            throw new StatementException("FOR: error while typechecking body", e);
        }
    }

    @Override
    public String toString() {
        return "for(" + varName + "=" + exp1 + ";" + varName + "<" + exp2 + ";" + varName + "=" + exp3 + ") " + body;
    }
}
