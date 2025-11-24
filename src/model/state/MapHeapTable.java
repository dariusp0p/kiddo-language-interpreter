package model.state;

import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapHeapTable implements HeapTable {
    private final KiddoDictionary<Integer, Value> heap;
    private int nextFreeAddress;

    public MapHeapTable() {
        this.heap = new KiddoHashMapDictionary<>(new HashMap<Integer, Value>());
        this.nextFreeAddress = 1; // Addresses start from 1
    }

    @Override
    public int allocate(Value value) {
        heap.put(nextFreeAddress, value);
        return nextFreeAddress++;
    }

    @Override
    public void update(int address, Value value) {
        if (!heap.containsKey(address)) {
            throw new IllegalArgumentException("Address " + address + " does not exist in the heap.");
        }
        heap.put(address, value);
    }

    @Override
    public Value get(int address) {
        if (!heap.containsKey(address)) {
            throw new IllegalArgumentException("Address " + address + " does not exist in the heap.");
        }
        return heap.get(address);
    }

    @Override
    public boolean contains(int address) {
        return heap.containsKey(address);
    }

    @Override
    public void deallocate(int address) {
        if (!heap.containsKey(address)) {
            throw new IllegalArgumentException("Address " + address + " does not exist in the heap.");
        }
        heap.remove(address);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heap.toMap();
    }

    @Override
    public void setContent(KiddoDictionary<Integer, Value> newContent) {
        heap.clear();
        newContent.keySet().forEach(key -> heap.put(key, newContent.get(key)));
        nextFreeAddress = newContent.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
