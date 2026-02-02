package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;

import java.util.Map;

public interface LockTable {
    int allocate(int value) throws AdtException;
    void update(int address, int value) throws AdtException;
    int get(int address) throws AdtException;
    boolean contains(int address) throws AdtException;
    void deallocate(int address) throws AdtException;
    Map<Integer, Integer> getContent();
    void setContent(KiddoDictionary<Integer, Integer> newContent) throws AdtException;
    boolean acquire(int address, int prgId) throws AdtException;
    void release(int address, int prgID) throws AdtException;
}
