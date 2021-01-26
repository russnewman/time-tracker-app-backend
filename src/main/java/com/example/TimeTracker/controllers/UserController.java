package com.example.TimeTracker.controllers;

import com.example.TimeTracker.payload.request.UpdateRequest;
import com.example.TimeTracker.service.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    @Autowired
    private UpdateUserService updateUserService;

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateRequest updateRequest){
        updateUserService.update(updateRequest);
        return ResponseEntity.ok("Updated successfully");
    }
}
