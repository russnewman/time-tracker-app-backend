package com.example.TimeTracker.service.Impl;


import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Cache {

    private static HashMap<String, Object> cache = new HashMap<>();

    public static HashMap<String, Object> getCache() {
        return cache;
    }

    public static void add(String key, Object value){
        cache.put(key, value);
    }

    public static Object getByKey(String key){
        return cache.get(key);
    }

    public static boolean isCached(String key){
        return cache.containsKey(key);
    }

    public static void evict(String key){

        Set<String> keys = cache.keySet()
                .stream()
                .filter(elem -> elem.contains(key)).collect(Collectors.toSet());
        keys.forEach(elem -> cache.remove(elem));
    }

}
