package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.type.Type;
import model.value.Value;

public interface SymbolTable {
    void define(String variableName, Value value) throws AdtException;
    boolean isDefined(String variableName) throws AdtException;
    Value lookup(String variableName) throws AdtException;
    Type getType(String variableName) throws AdtException;
    KiddoDictionary<String, Value> getContent();
    SymbolTable deepCopy() throws AdtException;
}
