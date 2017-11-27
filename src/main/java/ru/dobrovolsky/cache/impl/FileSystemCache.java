package ru.dobrovolsky.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dobrovolsky.cache.Cache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileSystemCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemCache.class);
    private final int cacheSize;
    private final Path tmpDir;
    private final String DIR_NAME = "SimpleCachingSystemTempDir";
    private Map<K, String> storage;

    public FileSystemCache(int cacheSize) throws IOException {
        this.cacheSize = cacheSize;
        this.storage = new ConcurrentHashMap<>(this.cacheSize);
        this.tmpDir = Files.createTempDirectory(DIR_NAME);
        this.tmpDir.toFile().deleteOnExit();
    }

    @Override
    public synchronized void cache(K key, V value) {
        LOGGER.info(LocalDateTime.now() + " : Trying to cache object: " + key + " to 2 level cache");

        if (storage.size() < cacheSize) {
            File tmpFile;
            try (ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(tmpFile = Files.createTempFile(tmpDir, "", "").toFile()))) {

                oo.writeObject(value);
                oo.flush();

                LOGGER.info(LocalDateTime.now() + " :   Object cached successfully: " + tmpFile + " ( key:" + key +
                        ")  to 2 level cache");

                storage.put(key, tmpFile.getName());
                return;
            } catch (IOException e) {
                LOGGER.error(LocalDateTime.now() + "    :   Can not cache object:   " + key + " to 2 level cache: " + e
                        .getMessage());

            }
        }

        LOGGER.info(LocalDateTime.now() + " : 2 level cache:    " + this.getClass().getSimpleName() + " is full, need" +
                " to reCache");
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized V extract(K key) {
        if (contains(key)) {
            String tmpFileName = storage.get(key);

            LOGGER.info(LocalDateTime.now() + " :   Trying to restore object:   " + tmpFileName + " from 2 level cache");

            try (FileInputStream fis = new FileInputStream(new File(tmpDir + File.separator + tmpFileName))) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                return (V) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error(LocalDateTime.now() + "    :  Can not restore object:  " + tmpFileName + " from 2 level " +
                        "cache " + e.getMessage());
            }
        }

        LOGGER.error(LocalDateTime.now() + "    :   Object: " + key + " does not exists in 2 level cache");
        return null;
    }

    @Override
    public boolean contains(K key) {
        return storage.containsKey(key);
    }

    @Override
    public synchronized void remove(K key) {
        String tmpFileName = storage.get(key);

        LOGGER.info(LocalDateTime.now() + " :   Trying to delete object:    " + tmpFileName + " from 2 level cache");

        File fileToDelete = new File(tmpDir + File.separator + tmpFileName);
        if (fileToDelete.delete()) {
            LOGGER.info(LocalDateTime.now() + " :   Object: " + tmpFileName +
                    " was deleted successfully from 2 level cache");
        } else {
            LOGGER.info(LocalDateTime.now() + " :   Can not delete object: " + tmpFileName + " from 2 level cache");
        }

        String value = storage.remove(key);
        if (value != null) {
            LOGGER.info(LocalDateTime.now() + " :   Deleted object:    key: " + key + " value: " + value + " from 2 " +
                    "level cache");
        }
    }

    @Override
    public void clear() throws IOException {
        Files.newDirectoryStream(tmpDir, path -> path.toFile().isFile()).forEach(path -> {
            if (path.toFile().delete()) {
                LOGGER.info(LocalDateTime.now() + " :   Object: " + path.toFile().getName() +
                        " was deleted successfully from 2 level cache");
            } else {
                LOGGER.info(LocalDateTime.now() + " :   Can not delete object: " + path.toFile().getName() +
                        " from 2 level cache");
            }
        });

        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void printCache() {
        for (Map.Entry<K, String> entry : storage.entrySet()) {
            LOGGER.info(LocalDateTime.now() + " :   key:    " + entry.getKey() + "  :   value:  " + entry.getValue());
        }
    }

    @Override
    public boolean isFull() {
        return size() == cacheSize;
    }
}
