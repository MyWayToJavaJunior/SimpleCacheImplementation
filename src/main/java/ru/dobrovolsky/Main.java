package ru.dobrovolsky;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.cachingSystem.CachingSystem;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static int CAPACITY;
    private static String STRATEGY;

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Wrong arguments line.");
            System.out.println();
            System.out.println("Try to use:");
            System.out.println();
            System.out.println("\"-capacity\"   -   set capacity of both caches.");
            System.out.println("                    Try using values greater than 0 and not too large to use both " +
                    "cache levels.");
            System.out.println("\"-strategy\"   -   set type of caching strategy for cache system implementation.");
            System.out.println("                    Try using one of the values: LRU, LFU.");
            System.out.println("                    If the parameter is incorrect, the LRU algorithm will be used by " +
                    "default.");
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].toUpperCase().equals("-capacity".toUpperCase())) {
                    CAPACITY = Integer.valueOf(args[i + 1]);
                }
                if (args[i].toUpperCase().equals("-strategy".toUpperCase())) {
                    STRATEGY = args[i + 1];
                }
            }

//            LOGGER.info(LocalDateTime.now() + " : Start 2 level caching system");
//
//            String str0 = "Test0";
//            String str1 = "Test1";
//            String str2 = "Test2";
//            String str3 = "Test3";
//            String str4 = "Test4";
//            String str5 = "Test5";
//            String str6 = "Test6";
//            String str7 = "Test7";
//
//            LOGGER.info(LocalDateTime.now() + " : Creating instance of 2 level caching system");
//            CachingSystem<Integer, String> cachingSystem = new CachingSystem<>(CAPACITY, STRATEGY);
//
//            cachingSystem.cache(0, str0);
//
//            String restoredStr0 = cachingSystem.extract(0);
//            LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
//                    restoredStr0);
//
//            cachingSystem.cache(1, str1);
//            cachingSystem.cache(2, str2);
//
//            restoredStr0 = cachingSystem.extract(0);
//            LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
//                    restoredStr0);
//
//            String restoredStr1 = cachingSystem.extract(1);
//            LOGGER.info(LocalDateTime.now() + " : Restored str1 after first filling of two level cache system:  " +
//                    restoredStr1);
//
//            String restoredStr2 = cachingSystem.extract(2);
//            LOGGER.info(LocalDateTime.now() + " : Restored str2 after first filling of two level cache system:  " +
//                    restoredStr2);
//
//            cachingSystem.cache(3, str3);
//
//            String restoredStr3 = cachingSystem.extract(3);
//            LOGGER.info(LocalDateTime.now() + " : Restored str3 after first filling of two level cache system:  " +
//                    restoredStr3);
//
//            cachingSystem.cache(4, str4);
//            cachingSystem.cache(5, str5);
//            cachingSystem.cache(6, str6);
//            cachingSystem.cache(7, str7);
//
//            restoredStr0 = cachingSystem.extract(0);
//            LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
//                    restoredStr0);
//
//            LOGGER.info(LocalDateTime.now() + " : Trying to restore all objects from cache system");
//
//            restoredStr0 = cachingSystem.extract(0);
//            LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
//                    restoredStr0);
//            restoredStr1 = cachingSystem.extract(1);
//            LOGGER.info(LocalDateTime.now() + " : Restored str1 after first filling of two level cache system:  " +
//                    restoredStr1);
//            restoredStr2 = cachingSystem.extract(2);
//            LOGGER.info(LocalDateTime.now() + " : Restored str2 after first filling of two level cache system:  " +
//                    restoredStr2);
//            restoredStr3 = cachingSystem.extract(3);
//            LOGGER.info(LocalDateTime.now() + " : Restored str3 after first filling of two level cache system:  " +
//                    restoredStr3);
//            String restoredStr4 = cachingSystem.extract(4);
//            LOGGER.info(LocalDateTime.now() + " : Restored str4 after first filling of two level cache system:  " +
//                    restoredStr4);
//            String restoredStr5 = cachingSystem.extract(5);
//            LOGGER.info(LocalDateTime.now() + " : Restored str5 after first filling of two level cache system:  " +
//                    restoredStr5);
//            String restoredStr6 = cachingSystem.extract(6);
//            LOGGER.info(LocalDateTime.now() + " : Restored str6 after first filling of two level cache system:  " +
//                    restoredStr6);
//            String restoredStr7 = cachingSystem.extract(7);
//            LOGGER.info(LocalDateTime.now() + " : Restored str7 after first filling of two level cache system:  " +
//                    restoredStr7);
//
//            LOGGER.info(LocalDateTime.now() + " : Checking cache state:");
//            cachingSystem.printCache();
//
//            LOGGER.info(LocalDateTime.now() + " : Closing up 2 level caching system");

            doWork();
        }
    }

    private static void doWork() throws IOException {
        LOGGER.info(LocalDateTime.now() + " : Start 2 level caching system");

        String str0 = "Test0";
        String str1 = "Test1";
        String str2 = "Test2";
        String str3 = "Test3";
        String str4 = "Test4";
        String str5 = "Test5";
        String str6 = "Test6";
        String str7 = "Test7";

        LOGGER.info(LocalDateTime.now() + " : Creating instance of 2 level caching system");
        CachingSystem<Integer, String> cachingSystem = new CachingSystem<>(CAPACITY, STRATEGY);

        cachingSystem.cache(0, str0);

        String restoredStr0 = cachingSystem.extract(0);
        LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
                restoredStr0);

        cachingSystem.cache(1, str1);
        cachingSystem.cache(2, str2);

        restoredStr0 = cachingSystem.extract(0);
        LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
                restoredStr0);

        String restoredStr1 = cachingSystem.extract(1);
        LOGGER.info(LocalDateTime.now() + " : Restored str1 after first filling of two level cache system:  " +
                restoredStr1);

        String restoredStr2 = cachingSystem.extract(2);
        LOGGER.info(LocalDateTime.now() + " : Restored str2 after first filling of two level cache system:  " +
                restoredStr2);

        cachingSystem.cache(3, str3);

        String restoredStr3 = cachingSystem.extract(3);
        LOGGER.info(LocalDateTime.now() + " : Restored str3 after first filling of two level cache system:  " +
                restoredStr3);

        cachingSystem.cache(4, str4);
        cachingSystem.cache(5, str5);
        cachingSystem.cache(6, str6);
        cachingSystem.cache(7, str7);

        restoredStr0 = cachingSystem.extract(0);
        LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
                restoredStr0);

        LOGGER.info(LocalDateTime.now() + " : Trying to restore all objects from cache system");

        restoredStr0 = cachingSystem.extract(0);
        LOGGER.info(LocalDateTime.now() + " : Restored str0 after first filling of two level cache system:  " +
                restoredStr0);
        restoredStr1 = cachingSystem.extract(1);
        LOGGER.info(LocalDateTime.now() + " : Restored str1 after first filling of two level cache system:  " +
                restoredStr1);
        restoredStr2 = cachingSystem.extract(2);
        LOGGER.info(LocalDateTime.now() + " : Restored str2 after first filling of two level cache system:  " +
                restoredStr2);
        restoredStr3 = cachingSystem.extract(3);
        LOGGER.info(LocalDateTime.now() + " : Restored str3 after first filling of two level cache system:  " +
                restoredStr3);
        String restoredStr4 = cachingSystem.extract(4);
        LOGGER.info(LocalDateTime.now() + " : Restored str4 after first filling of two level cache system:  " +
                restoredStr4);
        String restoredStr5 = cachingSystem.extract(5);
        LOGGER.info(LocalDateTime.now() + " : Restored str5 after first filling of two level cache system:  " +
                restoredStr5);
        String restoredStr6 = cachingSystem.extract(6);
        LOGGER.info(LocalDateTime.now() + " : Restored str6 after first filling of two level cache system:  " +
                restoredStr6);
        String restoredStr7 = cachingSystem.extract(7);
        LOGGER.info(LocalDateTime.now() + " : Restored str7 after first filling of two level cache system:  " +
                restoredStr7);

        LOGGER.info(LocalDateTime.now() + " : Checking cache state:");
        cachingSystem.printCache();

        LOGGER.info(LocalDateTime.now() + " : Closing up 2 level caching system");
    }
}
