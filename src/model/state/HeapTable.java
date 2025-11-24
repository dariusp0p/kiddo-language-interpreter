package model.state;

import model.adt.KiddoDictionary;
import model.value.Value;

import java.util.Map;

public interface HeapTable {
    int allocate(Value value);
    void update(int address, Value value);
    Value get(int address);
    boolean contains(int address);
    void deallocate(int address);
    Map<Integer, Value> getContent();
    void setContent(KiddoDictionary<Integer, Value> newContent);
}
