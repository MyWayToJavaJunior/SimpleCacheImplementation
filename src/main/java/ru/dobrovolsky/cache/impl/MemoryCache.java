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
    private static final int DEFAULT_CACHE_SIZE = 10;

    private Map<K, V> storage;
    private final int cacheSize;

    public MemoryCache() {
        this.cacheSize = DEFAULT_CACHE_SIZE;
        this.storage = new ConcurrentHashMap<>(this.cacheSize);
    }

    public MemoryCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.storage = new ConcurrentHashMap<>(this.cacheSize);
    }

    @Override
    public synchronized void cache(K key, V value) {
        LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to 1 level cache");
        if (storage.size() < cacheSize) {
            storage.put(key, value);
            return;
        }

        LOGGER.info(LocalDateTime.now() + " : 1 level cache:    " + this.getClass().getSimpleName()
                + " is full, need to reCache");
    }

    @Override
    public synchronized V extract(K key) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to restore object:   " + key + " from 1 level cache");
        return storage.get(key);
    }

    @Override
    public boolean contains(K key) {
        return storage.containsKey(key);
    }

    @Override
    public synchronized void remove(K key) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to delete object:    " + key + " from 1 level cache");
        V value = storage.remove(key);
        if (value != null) {
            LOGGER.info(LocalDateTime.now() + " :   Deleted object:    key: " + key + " value: " + value + " from 1 " +
                    "level cache");
        }
    }

    @Override
    public synchronized void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public synchronized void printCache() {
        for (Map.Entry<K, V> entry : storage.entrySet()) {
            LOGGER.info(LocalDateTime.now() + " :   key:    " + entry.getKey() + "  :   value:  " + entry.getValue());
        }
    }

    @Override
    public synchronized boolean isFull() {
        return size() == cacheSize;
    }
}
