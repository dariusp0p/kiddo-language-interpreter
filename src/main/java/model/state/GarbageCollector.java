package model.state;

import model.value.ReferenceValue;
import model.value.Value;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {
    /**
     * Performs safe garbage collection: returns a new heap containing only reachable addresses.
     */
    public static Map<Integer, Value> safeGarbageCollector(Set<Integer> roots, Map<Integer, Value> heap) {
        Set<Integer> reachable = getReachableAddresses(roots, heap);
        return heap.entrySet().stream()
                .filter(entry -> reachable.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Computes transitive closure of all addresses reachable from the root set.
     */
    private static Set<Integer> getReachableAddresses(Set<Integer> roots, Map<Integer, Value> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        boolean changed;

        do {
            changed = false;
            Set<Integer> newlyFound = heap.entrySet().stream()
                    .filter(entry -> reachable.contains(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(ReferenceValue.class::isInstance)
                    .map(value -> ((ReferenceValue) value).address())
                    .filter(addr -> !reachable.contains(addr))
                    .collect(Collectors.toSet());

            if (!newlyFound.isEmpty()) {
                reachable.addAll(newlyFound);
                changed = true;
            }

        } while (changed);

        return reachable;
    }

    /**
     * Extracts all addresses referenced in the SymbolTable.
     */
    public static Set<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(ReferenceValue.class::isInstance)
                .map(v -> ((ReferenceValue) v).address())
                .collect(Collectors.toSet());
    }
}
