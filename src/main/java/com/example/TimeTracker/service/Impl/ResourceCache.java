package com.example.TimeTracker.service.Impl;

import com.example.TimeTracker.dto.Resource;

import java.util.List;

public class ResourceCache extends Cache{
    public static List<Resource> getByKey(String key) {
        return  (List<Resource>) Cache.getByKey(key);
    }
}
