package ru.dobrovolsky.cache.strategies;

import ru.dobrovolsky.cache.strategies.comparator.SimpleComparator;

import java.util.Map;
import java.util.TreeMap;

public abstract class Strategy<K> {
    private Map<K, Long> storage;
    private TreeMap<K, Long> map;

    public Strategy() {
        this.storage = new TreeMap<>();
        this.map = new TreeMap<>(new SimpleComparator<>(storage));
    }

    protected Map<K, Long> getStorage() {
        return storage;
    }

    protected TreeMap<K, Long> getMap() {
        return map;
    }

    public abstract void cache(K key);

    public void remove(K key) {
        storage.remove(key);
        this.map = new TreeMap<>(new SimpleComparator<>(storage));
    }

    public boolean contains(K key) {
        return storage.containsKey(key);
    }

    public K getKeyToDelete() {
        map.putAll(storage);
        K key = map.firstKey();
        storage.remove(map.firstKey());
        map = new TreeMap<>(new SimpleComparator<>(storage));
        return key;
    }

    public void clear() {
        storage.clear();
    }
}
