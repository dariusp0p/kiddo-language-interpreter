package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface SemaphoreTable {
    int allocate(AbstractMap.SimpleEntry<Integer, List<Integer>> entry) throws AdtException;
    void update(int index, AbstractMap.SimpleEntry<Integer, List<Integer>> entry) throws AdtException;
    AbstractMap.SimpleEntry<Integer, List<Integer>> get(int index) throws AdtException;
    boolean contains(int index) throws AdtException;
    void deallocate(int index) throws AdtException;
    Map<Integer, AbstractMap.SimpleEntry<Integer, List<Integer>>> getContent();
    void setContent(KiddoDictionary<Integer, AbstractMap.SimpleEntry<Integer, List<Integer>>> newContent) throws AdtException;
}
