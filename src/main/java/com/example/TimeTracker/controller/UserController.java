package com.example.TimeTracker.controller;

import com.example.TimeTracker.exception.IncorrectPasswordException;
import com.example.TimeTracker.exception.UserAlreadyExistException;
import com.example.TimeTracker.payload.request.UpdatePasswordRequest;
import com.example.TimeTracker.payload.request.PersonInfo;
import com.example.TimeTracker.service.PersonService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private PersonService personService;


    @PostMapping("/update/info")
    public ResponseEntity<?> updateUserInfo(@RequestBody PersonInfo personInfo){
        try {
            personService.updateInfo(personInfo);
            return ResponseEntity.ok("Updated successfully!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        try {
            personService.updatePassword(updatePasswordRequest);
            return ResponseEntity.ok("The password was successfully updated!");
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/employee")
    public ResponseEntity<?> updateEmployee(@RequestBody PersonInfo personInfo){
        personService.updateEmployee(personInfo);
        return ResponseEntity.ok("Updated successfully!");
    }



    @PostMapping("/delete/employee")
    public ResponseEntity<?> deleteEmployee(@RequestBody JsonNode body){
        Long employeeId = body.get("employeeId").asLong();
        personService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Deleted successfully!");
    }



    @PostMapping("/addManager")
    public ResponseEntity<?> addManager(@RequestBody JsonNode body){
        Long userId = body.get("userId").asLong();
        Long managerId = body.get("managerId").asLong();
        personService.addOrDeleteManager(userId, managerId);
        return ResponseEntity.ok("Add successfully!");
    }

    @PostMapping("/deleteManager")
    public ResponseEntity<?> deleteManager(@RequestBody JsonNode body){
        Long userId = body.get("userId").asLong();
        personService.addOrDeleteManager(userId, null);
        return ResponseEntity.ok("Deleted successfully!");
    }
}
