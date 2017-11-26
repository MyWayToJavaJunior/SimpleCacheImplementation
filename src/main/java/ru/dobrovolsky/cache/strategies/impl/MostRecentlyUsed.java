package ru.dobrovolsky.cache.strategies.impl;

import ru.dobrovolsky.cache.strategies.Strategy;

public class MostRecentlyUsed<K> extends Strategy<K> {

    @Override
    public void cache(K key) {
        getStorage().put(key, System.nanoTime());
    }

    @Override
    public K getKeyToDelete() {
        getMap().putAll(getStorage());
        return getMap().lastKey();
    }
}
