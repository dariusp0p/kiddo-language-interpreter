package model.state;

import exceptions.AdtException;
import model.adt.KiddoArrayList;
import model.adt.KiddoList;
import model.value.Value;

import java.util.List;

public class ListOutput implements Output {
    private final KiddoList<Value> outputList;

    public ListOutput() {
        this.outputList = new KiddoArrayList<>();
    }

    @Override
    public void add(Value value) throws AdtException {
        outputList.add(value);
    }

    @Override
    public List<Value> getAll() throws AdtException {
        return outputList.asList();
    }

    @Override
    public String toString() {
        return outputList.toString();
    }
}
