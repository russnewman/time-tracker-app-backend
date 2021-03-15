package com.example.TimeTracker.controller;

import com.example.TimeTracker.dto.AddOrChangeCategoryRequest;
import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.model.Category;
import com.example.TimeTracker.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/employee")
    public ResponseEntity<?> updateCategory(
            @RequestParam Long employeeId,
            @RequestBody AddOrChangeCategoryRequest addOrChangeCategoryRequest){
        resourcesService.changeCategory(employeeId, addOrChangeCategoryRequest.getHost(),
                Category.of(addOrChangeCategoryRequest.getCategory()));
        return ResponseEntity.ok("Successfully");
    }

    @PostMapping("/employee")
    public ResponseEntity<?> addResourceWithCategory(
            @RequestParam Long employeeId,
            @RequestBody AddOrChangeCategoryRequest addOrChangeCategoryRequest){
        resourcesService.addResourceWithCategory(employeeId, addOrChangeCategoryRequest.getUrl(),
                Category.of(addOrChangeCategoryRequest.getCategory()));
        return ResponseEntity.ok("Successfully");
    }


    @PutMapping("/team")
    public ResponseEntity<?> updateCategoryTeam(
            @RequestParam Long managerId,
            @RequestBody AddOrChangeCategoryRequest addOrChangeCategoryRequest){
        resourcesService.changeCategoryForTeam(managerId, addOrChangeCategoryRequest.getHost(),
                Category.of(addOrChangeCategoryRequest.getCategory()));
        return ResponseEntity.ok("Successfully");
    }


    @PostMapping("/team")
    public ResponseEntity<?> addResourceWithCategoryTeam(
            @RequestParam Long managerId,
            @RequestBody AddOrChangeCategoryRequest addOrChangeCategoryRequest){
        resourcesService.addResourceWithCategoryForTeam(managerId, addOrChangeCategoryRequest.getUrl(),
                Category.of(addOrChangeCategoryRequest.getCategory()));
        return ResponseEntity.ok("Successfully");
    }
}
