package com.example.TimeTracker.service.Impl;

import com.example.TimeTracker.model.Category;

import java.util.HashMap;

public class EfficiencyCache extends Cache {

    public static HashMap<Category, int[]> getByKey(String key) {
        return  (HashMap<Category, int[]>) Cache.getByKey(key);
    }
}
