package ru.dobrovolsky.cache;

import java.io.IOException;
import java.util.Map;

public interface Cache<K, V> {
    void cache(K key, V value) throws IOException;

    void cacheAll(Map<? extends K, ? extends V> map);

    V extract(K key);

    boolean contains(K key);

    void remove(K key);

    void clear() throws IOException;

    int size();
}
