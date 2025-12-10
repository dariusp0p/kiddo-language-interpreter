package model.state;

import exceptions.AdtException;
import model.value.Value;

import java.util.List;

public interface Output {
    void add(Value value) throws AdtException;
    List<Value> getAll() throws AdtException;
}
