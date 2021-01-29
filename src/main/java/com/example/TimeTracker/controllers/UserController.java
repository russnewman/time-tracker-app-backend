package com.example.TimeTracker.controllers;

import com.example.TimeTracker.exception.IncorrectPasswordException;
import com.example.TimeTracker.exception.UserNotFoundException;
import com.example.TimeTracker.payload.request.UpdatePasswordRequest;
import com.example.TimeTracker.payload.request.PersonInfo;
import com.example.TimeTracker.service.EmployeeService;
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
        } catch (UserNotFoundException e) {
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
}
