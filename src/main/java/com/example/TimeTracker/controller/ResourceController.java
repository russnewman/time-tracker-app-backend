package com.example.TimeTracker.controller;

import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.service.EfficiencyService;
import com.example.TimeTracker.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;



@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourcesService resourcesService;

    @GetMapping("/team")
    public ResponseEntity<?> getSitesForAllTeam(
            @RequestParam Long userId,
            @RequestParam String date,
            @RequestParam String periodOfTime){
        return ResponseEntity.ok(resourcesService.getResourcesForAllTeam(userId, LocalDate.parse(date), PeriodOfTime.of(periodOfTime)));
    }


    @GetMapping("/employee")
    public ResponseEntity<?> getSitesForEmployee(
            @RequestParam Long employeeId,
            @RequestParam String date,
            @RequestParam String periodOfTime){
        return ResponseEntity.ok(resourcesService.getResourcesForEmployee(employeeId, LocalDate.parse(date), PeriodOfTime.of(periodOfTime)));
    }
}
