package com.example.TimeTracker.controller;


import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.service.EfficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/efficiency")
@CrossOrigin(origins="*")
public class EfficiencyController {

    @Autowired
    private EfficiencyService efficiencyService;

    //TODO Dont take into account Many Timezones
    @GetMapping("/team")
    public ResponseEntity<?> getEfficiencyForAllTeam(
            @RequestParam String date,
            @RequestParam String periodOfTime){
        return ResponseEntity.ok(efficiencyService.computeEfficiencyAllTeam(LocalDate.parse(date), PeriodOfTime.of(periodOfTime)));
    }

}
