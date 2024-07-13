package com.stephenshen.sscache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * cache entries.
 * @author stephenshen
 * @date 2024/7/7 13:50:17
 */
public class SSCache {

    Map<String, CacheEntry<?>> map = new HashMap<>();

    // =============== 1. string ===================
    public String get(String key) {
        CacheEntry<String> cacheEntry = (CacheEntry<String>) map.get(key);
        return cacheEntry.getValue();
    }
    public void set(String key, String value) {
        map.put(key, new CacheEntry<>(value));
    }

    public int del(String... keys) {
        return keys == null ? 0 : (int)Arrays.stream(keys)
                .map(map::remove).filter(Objects::nonNull).count();
    }

    public int exists(String... keys) {
        return keys == null ? 0 : (int) Arrays.stream(keys)
                .map(map::containsKey).filter(x -> x).count();
    }

    public String[] mget(String... keys) {
        return keys == null ? new String[0] : Arrays.stream(keys)
                .map(this::get).toArray(String[]::new);
    }

    public void mset(String[] keys, String[] values) {
        if (keys == null || keys.length == 0) return;
        for (int i = 0; i < keys.length; i++) {
            set(keys[i], values[i]);
        }
    }

    public int incr(String key) {
        String str = get(key);
        int val = 0;
        try {
            if (str != null) {
                val = Integer.parseInt(str);
            }
            val++;
            set(key, String.valueOf(val));
        } catch (NumberFormatException e) {
            throw e;
        }
        return val;
    }

    public int decr(String key) {
        String str = get(key);
        int val = 0;
        try {
            if (str != null) {
                val = Integer.parseInt(str);
            }
            val--;
            set(key, String.valueOf(val));
        } catch (NumberFormatException e) {
            throw e;
        }
        return val;
    }

    public Integer strlen(String key) {
        String val = get(key);
        return val == null ? 0 : val.length();
    }

    // =============== 1. string end ===================

    // =============== 2. list ===================

    public Integer lpush(String key, String[] vals) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedList<>());
            this.map.put(key, entry);
        }
        LinkedList<String> exist = entry.getValue();
        Arrays.stream(vals).forEach(exist::addFirst);
        return vals.length;
    }

    public String[] lpop(String key, int count) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) return null;
        LinkedList<String> exist = entry.getValue();
        if (exist == null) return null;

        int len = Math.min(exist.size(), count);
        String[] ret = new String[len];
        int index = 0;
        while (index < len) {
            ret[index++] = exist.removeFirst();
        }
        return ret;
    }

    public Integer rpush(String key, String[] vals) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedList<>());
            this.map.put(key, entry);
        }
        LinkedList<String> exist = entry.getValue();
        // Arrays.stream(vals).forEach(exist::addLast);
        exist.addAll(List.of(vals));
        return vals.length;
    }

    public String[] rpop(String key, int count) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) return null;
        LinkedList<String> exist = entry.getValue();
        if (exist == null) return null;

        int len = Math.min(exist.size(), count);
        String[] ret = new String[len];
        int index = 0;
        while (index < len) {
            ret[index++] = exist.removeLast();
        }
        return ret;
    }

    public Integer llen(String key) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) return 0;
        LinkedList<String> exist = entry.getValue();
        if (exist == null) return 0;
        return exist.size();
    }

    public String lindex(String key, int index) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) return null;
        LinkedList<String> exist = entry.getValue();
        if (exist == null) return null;
        if (index >= exist.size()) return null;
        return exist.get(index);
    }

    public String[] lrang(String key, int start, int end) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>) map.get(key);
        if (entry == null) return null;
        LinkedList<String> exist = entry.getValue();
        if (exist == null) return null;
        int size = exist.size();
        if (start >= size) return null;
        if (end >= size) end = size - 1;
        int len = Math.min(size, end - start + 1);
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = exist.get(start + i);
        }
        return ret;
    }

    // =============== 2. list end ===================

    // =============== 3. set start ===================

    public Integer sadd(String key, String[] vals) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) {
            entry = new CacheEntry<>(new LinkedHashSet<>());
            this.map.put(key, entry);
        }
        LinkedHashSet<String> exist = entry.getValue();
        exist.addAll(Arrays.asList(vals));
        return vals.length;
    }

    public String[] smembers(String key) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) return new String[0];
        LinkedHashSet<String> exist = entry.getValue();
        return exist.toArray(String[]::new);
    }

    public Integer scard(String key) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) return 0;
        LinkedHashSet<String> exist = entry.getValue();
        return exist.size();
    }

    public Integer sismember(String key, String val) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) return 0;
        LinkedHashSet<String> exist = entry.getValue();
        return exist.contains(val) ? 1 : 0;
    }

    public Integer srem(String key, String[] vals) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) return 0;
        LinkedHashSet<String> exist = entry.getValue();
        return vals == null ? 0 : (int)Arrays.stream(vals)
                .map(exist::remove).filter(x -> x).count();
    }

    Random random = new Random();

    public String[] spop(String key, int count) {
        CacheEntry<LinkedHashSet<String>> entry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (entry == null) return null;
        LinkedHashSet<String> exist = entry.getValue();
        if (exist == null) return null;

        int len = Math.min(exist.size(), count);
        String[] ret = new String[len];
        int index = 0;
        while (index < len) {
            String[] array = exist.toArray(String[]::new);
            String obj = array[random.nextInt(exist.size())];
            exist.remove(obj);
            ret[index++] = obj;
        }
        return ret;
    }

    // =============== 3. Set end ===================

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CacheEntry<T> {
        private T value;

    }
}
