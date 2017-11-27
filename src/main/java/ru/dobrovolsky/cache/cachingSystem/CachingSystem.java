package ru.dobrovolsky.cache.cachingSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.Cache;
import ru.dobrovolsky.cache.impl.FileSystemCache;
import ru.dobrovolsky.cache.impl.MemoryCache;
import ru.dobrovolsky.cache.strategies.Strategy;
import ru.dobrovolsky.cache.strategies.StrategyFactory;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CachingSystem<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachingSystem.class);
    private static final int DEFAULT_CACHE_SIZE = 10;
    private final int cacheSize;
    private MemoryCache<K, V> memoryCache;
    private FileSystemCache<K, V> fileSystemCache;
    private Strategy<K> strategy;

    public CachingSystem(int capacity, String strategy) throws IOException {
        if (capacity <= 0) {
            this.cacheSize = DEFAULT_CACHE_SIZE;
        } else {
            this.cacheSize = capacity;
        }

        this.memoryCache = new MemoryCache<>(this.cacheSize);
        this.fileSystemCache = new FileSystemCache<>(this.cacheSize);
        this.strategy = new StrategyFactory<K>().getStrategy(strategy);
    }

    public CachingSystem() throws IOException {
        this.cacheSize = DEFAULT_CACHE_SIZE;
        this.memoryCache = new MemoryCache<>(this.cacheSize);
        this.fileSystemCache = new FileSystemCache<>(this.cacheSize);
        this.strategy = new StrategyFactory<K>().getStrategy("");
    }

    @Override
    public synchronized void cache(K key, V value) throws IOException {
        if (memoryCache.contains(key)) {
            LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to first level cache");
            memoryCache.cache(key, value);
            fileSystemCache.remove(key);
            return;
        }
        if (!memoryCache.isFull()) {
            LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to first level cache");
            memoryCache.cache(key, value);
            fileSystemCache.remove(key);
            return;
        }
        if (fileSystemCache.contains(key)) {
            LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to second level cache");
            fileSystemCache.cache(key, value);
            memoryCache.remove(key);
            return;
        }
        if (!fileSystemCache.isFull()) {
            LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to second level cache");
            fileSystemCache.cache(key, value);
            memoryCache.remove(key);
            return;
        }

        strategy.cache(key);

        LOGGER.info(LocalDateTime.now() + " : Need to reCache");
        reCache(key, value);
    }

    private synchronized void reCache(K key, V value) {
        LOGGER.info(LocalDateTime.now() + " : Starting reCaching  process before:  " + key + " caching");
        K keyToDelete = strategy.getKeyToDelete();

        if (memoryCache.contains(keyToDelete)) {
            memoryCache.remove(keyToDelete);
            memoryCache.cache(key, value);
        }
        if (fileSystemCache.contains(keyToDelete)) {
            fileSystemCache.remove(keyToDelete);
            fileSystemCache.cache(key, value);
        }
    }

    @Override
    public synchronized V extract(K key) {
        if (memoryCache.contains(key)) {
            strategy.cache(key);
            return memoryCache.extract(key);
        }
        if (fileSystemCache.contains(key)) {
            strategy.cache(key);
            return fileSystemCache.extract(key);
        }

        return null;
    }

    @Override
    public boolean contains(K key) {
        return memoryCache.contains(key) || fileSystemCache.contains(key);
    }

    @Override
    public synchronized void remove(K key) {
        if (memoryCache.contains(key)) {
            LOGGER.info(LocalDateTime.now() + " :   Trying to delete object:    " + key + " from cache: " +
                    memoryCache.getClass().getSimpleName());
            memoryCache.remove(key);
        }
        if (fileSystemCache.contains(key)) {
            LOGGER.info(LocalDateTime.now() + " :   Trying to delete object:    " + key + " from cache: " +
                    fileSystemCache.getClass().getSimpleName());
            fileSystemCache.remove(key);
        }

        strategy.remove(key);
    }

    @Override
    public void clear() throws IOException {
        memoryCache.clear();
        fileSystemCache.clear();
        strategy.clear();
    }

    @Override
    public int size() {
        return memoryCache.size() + fileSystemCache.size();
    }

    @Override
    public void printCache() {
        memoryCache.printCache();
        fileSystemCache.printCache();
    }

    @Override
    public boolean isFull() {
        return memoryCache.isFull() & fileSystemCache.isFull();
    }
}