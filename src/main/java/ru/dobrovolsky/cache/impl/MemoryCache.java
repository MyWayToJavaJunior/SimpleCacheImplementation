package ru.dobrovolsky.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.Cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache<K, V> implements Cache<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryCache.class);
    private final static int DEFAULT_CACHE_SIZE = 10;

    private final Map<K, V> storage;

    public MemoryCache() {
        this.storage = new ConcurrentHashMap<>(DEFAULT_CACHE_SIZE);
    }

    public MemoryCache(int cacheSize) {
        this.storage = new HashMap<>(cacheSize);
    }

    @Override
    public void cache(K key, V value) {
        LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key);
        storage.put(key, value);
    }

    @Override
    public void cacheAll(Map<? extends K, ? extends V> map) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to cache multiple objects");
        storage.putAll(map);
    }

    @Override
    public V extract(K key) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to restore object:   " + key + " from cache");
        return this.storage.get(key);
    }

    @Override
    public boolean contains(K key) {
        return storage.containsKey(key);
    }

    @Override
    public void remove(K key) {
        LOGGER.info(LocalDateTime.now() + " :   Trying to delete object:    " + key + " from cache");
        storage.remove(key);
        LOGGER.info(LocalDateTime.now() + " :   Object: " + key + " was deleted successfully from cache");
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        LOGGER.info(LocalDateTime.now() + " :   Current cache { " + this.getClass().getSimpleName() + " } size: " + storage.size());
        return storage.size();
    }
}
