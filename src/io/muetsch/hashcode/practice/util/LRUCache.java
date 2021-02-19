package io.muetsch.hashcode.practice.util;

import java.util.Iterator;
import java.util.LinkedHashMap;

// https://stackoverflow.com/a/34206517/3112139

public class LRUCache {
    private final int capacity;
    private final LinkedHashMap<Integer, Long> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(16, 0.75f, true);
    }

    public long get(int key) {
        Long value = this.map.get(key);
        if (value == null) {
            value = -1L;
        }
        return value;
    }

    public void put(int key, long value) {
        if (
                !this.map.containsKey(key) &&
                        this.map.size() == this.capacity
        ) {
            Iterator<Integer> it = this.map.keySet().iterator();
            it.next();
            it.remove();
        }
        this.map.put(key, value);
    }
}
