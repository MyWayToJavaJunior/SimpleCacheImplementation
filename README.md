# SimpleCacheImplementation

- Create a configurable two-level cache (for caching Objects).
Level 1 is memory, level 2 is filesystem. Config params should let one
specify the cache strategies and max sizes of level  1 and 2.

    - Релизовано сохранение объектов в 2 уровня кэша с последующим
    их восстановлением;
    - Реализованы 2 алгоритма кеширования: Least recently used,
    Least-Frequently Used.