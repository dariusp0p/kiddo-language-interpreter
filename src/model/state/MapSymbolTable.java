package model.state;

import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.type.Type;
import model.value.Value;
import utilities.SymbolTableException;

public class MapSymbolTable implements SymbolTable {
    private final KiddoDictionary<String, Value> map = new KiddoHashMapDictionary<>();

    private void validateVariableName(String variableName) {
        if (variableName == null || variableName.isBlank()) {
            throw new SymbolTableException("Invalid variable name!");
        }
    }

    private void validateValue(Value value) {
        if (value == null) {
            throw new SymbolTableException("Invalid variable value!");
        }
    }

    @Override
    public void define(String variableName, Value value) {
        validateVariableName(variableName);
        validateValue(value);
        map.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        validateVariableName(variableName);
        return map.containsKey(variableName);
    }

    @Override
    public Value lookup(String variableName) {
        validateVariableName(variableName);
        if (!isDefined(variableName)) throw new SymbolTableException("Variable \"" + variableName + "\" is not defined!");
        return map.get(variableName);
    }

    @Override
    public Type getType(String variableName) {
        validateVariableName(variableName);
        if (!isDefined(variableName)) throw new SymbolTableException("Variable \"" + variableName + "\" is not defined!");
        return map.get(variableName).getType();
    }

    @Override
    public void update(String variableName, Value value) {
        validateVariableName(variableName);
        validateValue(value);
        if (isDefined(variableName)) {
            map.put(variableName, value);
        } else {
            define(variableName, value);
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
