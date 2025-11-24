package model.state;

import model.adt.KiddoDictionary;
import model.type.Type;
import model.value.Value;

import java.lang.invoke.StringConcatFactory;

public interface SymbolTable {
    void define(String variableName, Value value);
    boolean isDefined(String variableName);
    Value lookup(String variableName);
    Type getType(String variableName);
    void update(String variableName, Value value);
    KiddoDictionary<String, Value> getContent();
}
