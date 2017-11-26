package ru.dobrovolsky.cache.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.strategies.impl.LeastFrequentlyUsed;
import ru.dobrovolsky.cache.strategies.impl.LeastRecentlyUsed;
import ru.dobrovolsky.cache.strategies.impl.MostRecentlyUsed;

import java.time.LocalDateTime;

public class StrategyFactory<K> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyFactory.class);

    public Strategy<K> getStrategy(String type) {
        if (Strategies.valueOf(type.toUpperCase()) == Strategies.LEAST_RECENTLY_USED) {
            LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + LeastRecentlyUsed.class.getSimpleName());
            return new LeastRecentlyUsed<>();
        }
        if (Strategies.valueOf(type.toUpperCase()) == Strategies.LEAST_FREQUENTLY_USED) {
            LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + LeastFrequentlyUsed.class.getSimpleName());
            return new LeastFrequentlyUsed<>();
        }
        if (Strategies.valueOf(type.toUpperCase()) == Strategies.MOST_RECENTLY_USED) {
            LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + MostRecentlyUsed.class.getSimpleName());
            return new MostRecentlyUsed<>();
        }

        LOGGER.info(LocalDateTime.now() + " : Creating strategy: " + LeastRecentlyUsed.class.getSimpleName());
        return new LeastRecentlyUsed<>();
    }
}
