package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class MapLockTable implements LockTable {
    private final KiddoDictionary<Integer, Integer> table;
    private int nextFree;
    private final ReentrantLock lock;

    public MapLockTable() {
        this.table = new KiddoHashMapDictionary<>();
        this.nextFree = 1;
        this.lock = new ReentrantLock();
    }

    @Override
    public int allocate(int value) throws AdtException {
        lock.lock();
        try {
            int addr = nextFree++;
            table.put(addr, value);
            return addr;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(int address, int value) throws AdtException {
        lock.lock();
        try {
            if (!table.containsKey(address)) throw new AdtException("Invalid lock address: " + address);
            table.put(address, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(int address) throws AdtException {
        lock.lock();
        try {
            return table.get(address);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(int address) throws AdtException {
        lock.lock();
        try {
            return table.containsKey(address);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deallocate(int address) throws AdtException {
        lock.lock();
        try {
            if (!table.containsKey(address)) throw new AdtException("Invalid lock address: " + address);
            table.remove(address);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Integer, Integer> getContent() {
        lock.lock();
        try {
            return table.toMap();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setContent(KiddoDictionary<Integer, Integer> newContent) throws AdtException {
        if (newContent == null) throw new AdtException("New content must not be null");
        lock.lock();
        try {
            table.clear();
            for (Integer k : newContent.keySet()) {
                table.put(k, newContent.get(k));
            }
            nextFree = newContent.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
        } finally {
            lock.unlock();
        }
    }

    // Atomic acquire: if entry exists and value == -1, set to prgId and return true;
    // if entry exists and value != -1 return false; if entry doesn't exist throw.
    @Override
    public boolean acquire(int address, int prgId) throws AdtException {
        lock.lock();
        try {
            if (!table.containsKey(address)) throw new AdtException("Invalid lock address: " + address);
            Integer current = table.get(address);
            if (current == null) throw new AdtException("Invalid lock entry: " + address);
            if (current == -1) {
                table.put(address, prgId);
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void release(int address, int prgId) throws AdtException {
        lock.lock();
        try {
            if (!table.containsKey(address)) throw new AdtException("Invalid lock address: " + address);
            Integer current = table.get(address);
            if (current == null) throw new AdtException("Invalid lock entry: " + address);
            if (current == prgId) {
                table.put(address, -1);
            }
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
