package model.statement;

import exceptions.AdtException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expression.Expression;
import model.state.HeapTable;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.ReferenceValue;
import model.value.Value;

public record HeapWriteStatement(String varName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        if (programState == null) throw new StatementException("wH: ProgramState cannot be null");
        if (varName == null || varName.isBlank()) throw new StatementException("wH: variable name cannot be null or blank");

        SymbolTable symbolTable = programState.symbolTable();
        HeapTable heapTable = programState.heapTable();

        try {
            if (!symbolTable.isDefined(varName)) throw new StatementException("wH: variable \"" + varName + "\" is not defined");
        } catch (AdtException e) {
            throw new StatementException("wH: failed checking if variable is defined: " + varName, e);
        }

        Value varValue;
        try {
            varValue = symbolTable.lookup(varName);
        } catch (AdtException e) {
            throw new StatementException("wH: failed to lookup variable \"" + varName + "\"", e);
        }

        if (!(varValue instanceof ReferenceValue refValue)) {
            throw new StatementException("wH: variable \"" + varName + "\" is not a reference value");
        }

        int address = refValue.address();

        try {
            if (!heapTable.contains(address)) {
                throw new StatementException("wH: address " + address + " is not allocated in the heap");
            }
        } catch (AdtException e) {
            throw new StatementException("wH: failed checking heap for address " + address, e);
        }

        Value evaluatedValue;
        try {
            evaluatedValue = expression.evaluate(symbolTable, heapTable);
        } catch (ExpressionException e) {
            throw new StatementException("wH: failed to evaluate expression " + expression, e);
        }

        if (!evaluatedValue.getType().equals(refValue.locationType())) {
            throw new StatementException(
                    "wH: type mismatch — variable \"" + varName + "\" expects " +
                            refValue.locationType() + " but expression evaluated to " +
                            evaluatedValue.getType()
            );
        }

        try {
            heapTable.update(address, evaluatedValue);
        } catch (AdtException e) {
            throw new StatementException("wH: failed to update heap at address " + address, e);
        }

        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
}
