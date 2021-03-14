package com.example.TimeTracker.service;

import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.dto.Resource;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ResourcesService {
    Map<String, List<Resource>> getResourcesForAllTeam(Long userId, LocalDate date, PeriodOfTime periodOfTime);
    List<Resource> getResourcesForEmployee(Long employeeId, LocalDate date, PeriodOfTime periodOfTime);
}
