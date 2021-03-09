package com.example.TimeTracker.controller;


import com.example.TimeTracker.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/efficiency")
public class LogsController {

    @Autowired
    private LogsService logsService;


    @GetMapping("/team")
    public ResponseEntity<?> getEfficiencyForAllTeam(
                @RequestParam Long userId,
                @RequestParam LocalDate date){
        return ResponseEntity.ok(logsService.computeEfficiencyAllTeam(userId, date));
    }


    @GetMapping("/employee")
    public ResponseEntity<?> getLogsByPersonAndDate(
            @RequestParam String employeeEmail,
            @RequestParam Long employeeId,
            @RequestParam LocalDate date
    ){
        return ResponseEntity.ok(logsService.computeEfficiencyByUserAndDate(employeeId, date));
//

    }
}
