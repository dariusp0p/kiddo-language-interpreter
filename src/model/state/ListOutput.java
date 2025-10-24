package model.state;

import model.adt.KiddoArrayList;
import model.adt.KiddoList;
import model.value.Value;

public class ListOutput implements Output {
    private final KiddoList<Value> outputList;
    public ListOutput() { outputList = new KiddoArrayList<>(); }

    @Override
    public void add(Value value) { outputList.add(value); }

    @Override
    public String toString() { return outputList.toString(); }
}
