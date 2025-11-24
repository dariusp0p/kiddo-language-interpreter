package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.state.HeapTable;
import model.state.SymbolTable;
import model.value.ReferenceValue;
import model.value.Value;
import model.type.ReferenceType;

public record NewStatement(String varName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState programState) {
        SymbolTable symTable = programState.symbolTable();
        HeapTable heapTable = programState.heapTable();

        if (!symTable.isDefined(varName)) {
            throw new RuntimeException("Variable " + varName + " is not defined in the Symbol Table.");
        }
        Value varValue = symTable.lookup(varName);
        if (!(varValue.getType() instanceof ReferenceType refType)) {
            throw new RuntimeException("Variable " + varName + " is not of ReferenceType.");
        }

        Value evaluatedValue = expression.evaluate(symTable, heapTable);

        if (!evaluatedValue.getType().equals(refType.getInner())) {
            throw new RuntimeException("Type of the evaluated expression does not match the locationType of " + varName + ".");
        }

        int newAddress = heapTable.allocate(evaluatedValue);

        symTable.update(varName, new ReferenceValue(newAddress, refType.getInner()));

        return programState;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }
}
