package ru.dobrovolsky.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.Cache;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryCache.class);
    private final int cacheSize;
    private Map<K, V> storage;

    public MemoryCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.storage = new ConcurrentHashMap<>(this.cacheSize);
    }

    @Override
    public void cache(K key, V value) {
        LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to 1 level cache");
        if (storage.size() < cacheSize) {
            storage.put(key, value);
            return;
        }

        LOGGER.info(LocalDateTime.now() + " : 1 level cache:    " + this.getClass().getSimpleName()
                + " is full, need to reCache");
    }

    @Override
    public V extract(K key) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to restore object:   " + key + " from 1 level cache");
        return storage.get(key);
    }

    @Override
    public boolean contains(K key) {
        return storage.containsKey(key);
    }

    @Override
    public void remove(K key) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to delete object:    " + key + " from 1 level cache");
        V value = storage.remove(key);
        if (value != null) {
            LOGGER.info(LocalDateTime.now() + " :   Deleted object:    key: " + key + " value: " + value + " from 1 " +
                    "level cache");
        }
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void printCache() {
        for (Map.Entry<K, V> entry : storage.entrySet()) {
            LOGGER.info(LocalDateTime.now() + " :   key:    " + entry.getKey() + "  :   value:  " + entry.getValue());
        }
    }

    @Override
    public boolean isFull() {
        return size() == cacheSize;
    }
}
