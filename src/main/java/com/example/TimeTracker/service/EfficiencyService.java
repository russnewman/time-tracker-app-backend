package com.example.TimeTracker.service;

import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.model.Category;

import java.time.LocalDate;
import java.util.HashMap;

public interface EfficiencyService {
    public HashMap<Category, int[]> computeEfficiencyByUserAndDate(Long userId, LocalDate date);
    public HashMap<Long, HashMap<String,HashMap<Category, int[]>>> computeEfficiencyAllTeam(Long userId, LocalDate date, PeriodOfTime periodOfTime);

//    public HashMap<String, List<Integer>> getEfficiencyByEmployeeAndDate(String personEmail, LocalDateTime date);
}
