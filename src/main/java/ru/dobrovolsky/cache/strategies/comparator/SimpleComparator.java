package ru.dobrovolsky.cache.strategies.comparator;

import java.util.Comparator;
import java.util.Map;

public class SimpleComparator<K> implements Comparator<K> {
    private Map<K, Long> map;

    public SimpleComparator(Map<K, Long> map) {
        this.map = map;
    }

    @Override
    public int compare(K key1, K key2) {
        return map.get(key1).compareTo(map.get(key2));
    }
}
