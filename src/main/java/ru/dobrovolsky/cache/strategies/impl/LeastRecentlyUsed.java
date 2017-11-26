package ru.dobrovolsky.cache.strategies.impl;

import ru.dobrovolsky.cache.strategies.Strategy;

public class LeastRecentlyUsed<K> extends Strategy<K> {

    @Override
    public void cache(K key) {
        getStorage().put(key, System.nanoTime());
    }
}
