package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.value.Value;

import java.util.Map;

public interface HeapTable {
    int allocate(Value value) throws AdtException;
    void update(int address, Value value) throws AdtException;
    Value get(int address) throws AdtException;
    boolean contains(int address) throws AdtException;
    void deallocate(int address) throws AdtException;
    Map<Integer, Value> getContent();
    void setContent(KiddoDictionary<Integer, Value> newContent) throws AdtException;
}
