package model.state;

import exceptions.AdtException;
import model.value.Value;

public interface Output {
    void add(Value value) throws AdtException;
}
