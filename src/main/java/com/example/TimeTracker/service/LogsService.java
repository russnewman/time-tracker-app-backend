package com.example.TimeTracker.service;

import com.example.TimeTracker.model.Category;

import java.time.LocalDate;
import java.util.HashMap;

public interface LogsService {
    public HashMap<Category, int[]> computeEfficiencyByUserAndDate(Long userId, LocalDate date);
    public HashMap<Long, HashMap<Category, int[]>> computeEfficiencyAllTeam(Long userId, LocalDate date);

//    public HashMap<String, List<Integer>> getEfficiencyByEmployeeAndDate(String personEmail, LocalDateTime date);
}
