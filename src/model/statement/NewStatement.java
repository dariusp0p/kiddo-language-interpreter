package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.HeapTable;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;

public record NewStatement(String varName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("new: ProgramState cannot be null");
        if (varName == null || varName.isBlank()) throw new StatementException("new: variable name cannot be null or blank");

        SymbolTable symTable = programState.symbolTable();
        HeapTable heapTable = programState.heapTable();

        try {
            if (!symTable.isDefined(varName)) {
                throw new StatementException("new: variable \"" + varName + "\" is not defined");
            }
        } catch (AdtException e) {
            throw new StatementException("new: failed checking if variable \"" + varName + "\" is defined", e);
        }

        Value varValue;
        try {
            varValue = symTable.lookup(varName);
        } catch (AdtException e) {
            throw new StatementException("new: failed to lookup variable \"" + varName + "\"", e);
        }

        if (!(varValue.getType() instanceof ReferenceType refType)) {
            throw new StatementException("new: variable \"" + varName + "\" is not of ReferenceType");
        }

        Value evaluatedValue;
        try {
            evaluatedValue = expression.evaluate(symTable, heapTable);
        } catch (ExpressionException e) {
            throw new StatementException("new: failed to evaluate expression " + expression, e);
        }

        if (!evaluatedValue.getType().equals(refType.getInner())) {
            throw new StatementException("new: type mismatch — variable \"" + varName
                    + "\" expects inner type " + refType.getInner()
                    + " but expression evaluated to type " + evaluatedValue.getType());
        }

        int address;
        try {
            address = heapTable.allocate(evaluatedValue);
        } catch (AdtException e) {
            throw new StatementException("new: failed to allocate value in heap", e);
        }

        try {
            symTable.define(varName, new ReferenceValue(address, refType.getInner()));
        } catch (AdtException e) {
            throw new StatementException("new: failed to update variable \"" + varName + "\"", e);
        }

        return programState;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }
}
