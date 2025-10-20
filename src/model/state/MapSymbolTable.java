package model.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import model.type.Type;
import model.value.Value;

public class MapSymbolTable implements SymbolTable {
    private final Map<String, Value> map = new HashMap<>();

    @Override
    public void define(String name, Value value) {
        if (name == null) throw new NullPointerException("name");
        map.put(name, value);
    }

    @Override
    public Optional<Value> lookup(String name) {
        return Optional.ofNullable(map.get(name));
    }

    @Override
    public boolean isDefined(String variableName) {
        return map.containsKey(variableName);
    }

    @Override
    public Type getType(String variableName) {
        return lookup(variableName)
                .map(Value::getType)
                .orElseThrow(() -> new IllegalArgumentException("Variable not defined: " + variableName));
    }

    @Override
    public void update(String variableName, Value value) {
        if (map.containsKey(variableName)) {
            map.put(variableName, value);
        } else {
            define(variableName, value); // same fallback as your MapSymbolTable
        }
    }

    public void clear() {
        map.clear();
    }
}