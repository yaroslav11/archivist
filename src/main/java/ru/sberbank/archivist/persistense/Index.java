package ru.sberbank.archivist.persistense;

import java.util.*;

public abstract class Index<K, V> {
    private Map<K, List<V>> index = new HashMap<>();

    public void add(K key, V value) {
        if (!index.containsKey(key)){
            index.put(key, new ArrayList<>());
        }
        index.get(key).add(value);
    }
}
