package ru.dobrovolsky.cache.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.strategies.impl.LeastFrequentlyUsed;
import ru.dobrovolsky.cache.strategies.impl.LeastRecentlyUsed;

import java.time.LocalDateTime;

public class StrategyFactory<K> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyFactory.class);

    public Strategy<K> getStrategy(String type) {
        if (Strategies.valueOf(type.toUpperCase()) == Strategies.LRU) {
            LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + LeastRecentlyUsed.class.getSimpleName());
            return new LeastRecentlyUsed<>();
        }
        if (Strategies.valueOf(type.toUpperCase()) == Strategies.LFU) {
            LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + LeastFrequentlyUsed.class.getSimpleName());
            return new LeastFrequentlyUsed<>();
        }

        LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + LeastRecentlyUsed.class.getSimpleName());
        return new LeastRecentlyUsed<>();
    }
}
