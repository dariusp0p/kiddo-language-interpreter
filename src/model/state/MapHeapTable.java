package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.value.Value;

import java.util.Map;

public class MapHeapTable implements HeapTable {
    private final KiddoDictionary<Integer, Value> heap;
    private int nextFreeAddress;

    public MapHeapTable() {
        this.heap = new KiddoHashMapDictionary<>();
        this.nextFreeAddress = 1;
    }

    @Override
    public int allocate(Value value) throws AdtException {
        if (value == null) throw new AdtException("Value must not be null");
        int address = nextFreeAddress++;
        heap.put(address, value);
        return address;
    }

    @Override
    public void update(int address, Value value) throws AdtException {
        if (value == null) throw new AdtException("Value must not be null");
        if (!heap.containsKey(address)) throw new AdtException("Invalid heap address: " + address);
        heap.put(address, value);
    }

    @Override
    public Value get(int address) throws AdtException {
        return heap.get(address);
    }

    @Override
    public boolean contains(int address) throws AdtException {
        return heap.containsKey(address);
    }

    @Override
    public void deallocate(int address) throws AdtException {
        if (!heap.containsKey(address)) throw new AdtException("Invalid heap address: " + address);
        heap.remove(address);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heap.toMap();
    }

    @Override
    public void setContent(KiddoDictionary<Integer, Value> newContent) throws AdtException {
        if (newContent == null) throw new AdtException("New content must not be null");
        heap.clear();
        for (Integer address : newContent.keySet()) {
            heap.put(address, newContent.get(address));
        }
        nextFreeAddress =
                newContent.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
