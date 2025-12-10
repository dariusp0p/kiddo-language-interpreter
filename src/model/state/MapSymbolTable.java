package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.type.Type;
import model.value.Value;

public class MapSymbolTable implements SymbolTable {
    private final KiddoDictionary<String, Value> map =
            new KiddoHashMapDictionary<>();

    @Override
    public void define(String name, Value value) throws AdtException {
        if (invalidVariableName(name)) throw new AdtException("Invalid variable name");
        if (invalidValue(value)) throw new AdtException("Invalid value");
        map.put(name, value);
    }

    @Override
    public boolean isDefined(String name) throws AdtException {
        if (invalidVariableName(name)) throw new AdtException("Invalid variable name");
        return map.containsKey(name);
    }

    @Override
    public Value lookup(String name) throws AdtException {
        if (invalidVariableName(name)) throw new AdtException("Invalid variable name");
        return map.get(name);
    }

    @Override
    public Type getType(String name) throws AdtException {
        return lookup(name).getType();
    }

    @Override
    public KiddoDictionary<String, Value> getContent() {
        return map;
    }

    @Override
    public SymbolTable deepCopy() throws AdtException {
        MapSymbolTable copy = new MapSymbolTable();
        try {
            for (var entry : map.toMap().entrySet()) {
                copy.define(entry.getKey(), entry.getValue().deepCopy());
            }
        } catch (AdtException _) {
            throw new AdtException("Failed to deep copy symbol table");
        }
        return copy;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    private boolean invalidVariableName(String name) {
        return name == null || name.isBlank();
    }

    private boolean invalidValue(Value value) {
        return (value == null);
    }
}
