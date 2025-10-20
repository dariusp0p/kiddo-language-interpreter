package model.state;

import model.type.Type;
import model.value.Value;

import java.util.Optional;

public interface SymbolTable {
    void define(String variableName, Value value);
    Optional<Value> lookup(String variableName);
    boolean isDefined(String variableName);
    Type getType(String variableName);
    void update(String variableName, Value value);
}
