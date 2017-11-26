package ru.dobrovolsky.cache.strategies.impl;

import ru.dobrovolsky.cache.strategies.Strategy;

public class LeastFrequentlyUsed<K> extends Strategy<K> {

    @Override
    public void cache(K key) {
        long freq = 1;
        if (getStorage().containsKey(key)) {
            freq = getStorage().get(key) + 1;
        }

        getStorage().put(key, freq);
    }
}
