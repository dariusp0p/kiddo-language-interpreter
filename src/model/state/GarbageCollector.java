package model.state;


import model.value.ReferenceValue;
import model.value.Value;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {

    public static Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        List<Integer> reachableAddresses = getReachableAddresses(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<Integer> getReachableAddresses(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        Set<Integer> addresses = new HashSet<>(symTableAddr);
        boolean changed;

        do {
            changed = false;
            Set<Integer> newAddresses = heap.entrySet().stream()
                    .filter(e -> addresses.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(ReferenceValue.class::isInstance)
                    .map(v -> ((ReferenceValue) v).address())
                    .collect(Collectors.toSet());

            changed = addresses.addAll(newAddresses);
        } while (changed);

        return new ArrayList<>(addresses);
    }

    public static List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(ReferenceValue.class::isInstance)
                .map(v -> ((ReferenceValue) v).address())
                .collect(Collectors.toList());
    }
}
