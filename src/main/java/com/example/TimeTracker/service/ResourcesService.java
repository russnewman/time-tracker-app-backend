package com.example.TimeTracker.service;

import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.dto.Resource;
import com.example.TimeTracker.model.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ResourcesService {
    Map<String, List<Resource>> getResourcesForAllTeam(LocalDate date, PeriodOfTime periodOfTime);
    List<Resource> getResourcesForEmployee(Long employeeId, LocalDate date, PeriodOfTime periodOfTime);
    List<Resource> getResourcesWithCategoryForEmployee(Long employeeId);

    void changeCategory(Long employeeId, String host, Category category);
    void addResourceWithCategory(Long employeeId, String url, Category category);

    void changeCategoryForTeam(Long managerId, String host, Category category);
    void addResourceWithCategoryForTeam(Long managerId, String url, Category category);
}
