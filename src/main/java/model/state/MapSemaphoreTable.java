package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapSemaphoreTable implements SemaphoreTable {
    private final KiddoDictionary<Integer, AbstractMap.SimpleEntry<Integer, List<Integer>>> table;
    private int nextIndex;
    private final Lock lock = new ReentrantLock();

    public MapSemaphoreTable() {
        this.table = new KiddoHashMapDictionary<>();
        this.nextIndex = 1;
    }

    @Override
    public int allocate(AbstractMap.SimpleEntry<Integer, List<Integer>> entry) throws AdtException {
        if (entry == null) throw new AdtException("Entry must not be null");
        lock.lock();
        try {
            int index = nextIndex++;
            List<Integer> ownersCopy = entry.getValue() == null ? new ArrayList<>() : new ArrayList<>(entry.getValue());
            table.put(index, new AbstractMap.SimpleEntry<>(entry.getKey(), ownersCopy));
            return index;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(int index, AbstractMap.SimpleEntry<Integer, List<Integer>> entry) throws AdtException {
        if (entry == null) throw new AdtException("Entry must not be null");
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new AdtException("Invalid semaphore index: " + index);
            List<Integer> ownersCopy = entry.getValue() == null ? new ArrayList<>() : new ArrayList<>(entry.getValue());
            table.put(index, new AbstractMap.SimpleEntry<>(entry.getKey(), ownersCopy));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, List<Integer>> get(int index) throws AdtException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new AdtException("Invalid semaphore index: " + index);
            return table.get(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(int index) throws AdtException {
        lock.lock();
        try {
            return table.containsKey(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deallocate(int index) throws AdtException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new AdtException("Invalid semaphore index: " + index);
            table.remove(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Integer, AbstractMap.SimpleEntry<Integer, List<Integer>>> getContent() {
        lock.lock();
        try {
            return table.toMap();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setContent(KiddoDictionary<Integer, AbstractMap.SimpleEntry<Integer, List<Integer>>> newContent) throws AdtException {
        if (newContent == null) throw new AdtException("New content must not be null");
        lock.lock();
        try {
            table.clear();
            for (Integer index : newContent.keySet()) {
                AbstractMap.SimpleEntry<Integer, List<Integer>> entry = newContent.get(index);
                List<Integer> ownersCopy = entry.getValue() == null ? new ArrayList<>() : new ArrayList<>(entry.getValue());
                table.put(index, new AbstractMap.SimpleEntry<>(entry.getKey(), ownersCopy));
            }
            nextIndex = newContent.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            return table.toString();
        } finally {
            lock.unlock();
        }
    }
}
