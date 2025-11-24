package model.statement;

import model.expression.Expression;
import model.state.HeapTable;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.value.ReferenceValue;
import model.value.Value;

public record HeapWriteStatement(String varName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        SymbolTable symbolTable = programState.symbolTable();
        HeapTable heapTable = programState.heapTable();

        if (!symbolTable.isDefined(varName)) {
            throw new RuntimeException("Variable " + varName + " is not defined in the SymbolTable.");
        }

        Value value = symbolTable.lookup(varName);

        if (!(value instanceof ReferenceValue refValue)) {
            throw new RuntimeException("Variable " + varName + " is not of ReferenceType.");
        }

        int address = refValue.address();

        if (!heapTable.contains(address)) {
            throw new RuntimeException("Address " + address + " is not defined in the Heap.");
        }

        Value evaluatedValue = expression.evaluate(symbolTable, heapTable);

        if (!evaluatedValue.getType().equals(refValue.locationType())) {
            throw new RuntimeException("Type of the evaluated expression does not match the locationType of the variable.");
        }

        heapTable.update(address, evaluatedValue);

        return programState;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
}
