package ru.dobrovolsky.cache;

import java.io.IOException;

public interface Cache<K, V> {
    void cache(K key, V value) throws IOException;

    V extract(K key);

    boolean contains(K key);

    void remove(K key);

    void clear() throws IOException;

    int size();

    void printCache();

    boolean isFull();
}
