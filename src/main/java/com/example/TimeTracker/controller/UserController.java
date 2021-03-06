package com.example.TimeTracker.controller;

import com.example.TimeTracker.exception.IncorrectPasswordException;
import com.example.TimeTracker.exception.UserAlreadyExistException;
import com.example.TimeTracker.dto.UpdatePasswordRequest;
import com.example.TimeTracker.dto.PersonInfo;
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

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(){
        return ResponseEntity.ok(personService.getUserInfo());
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(){
        return ResponseEntity.ok(personService.getEmployees());
    }

    @GetMapping("/getManager")
    public ResponseEntity<?> getManager(){
        return ResponseEntity.ok(personService.getManager());
    }

    @GetMapping("/getAllManagers")
    public ResponseEntity<?> getManagers(){
        return ResponseEntity.ok(personService.getAllManagers());
    }

    @PostMapping("/update/info")
    public ResponseEntity<?> updateUserInfo(@RequestBody PersonInfo personInfo){
        try {
            personService.updateInfo(personInfo);
            return ResponseEntity.ok("Информация обновлена!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        try {
            personService.updatePassword(updatePasswordRequest);
            return ResponseEntity.ok("Пароль был успешно изменен!");
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update/employee")
    public ResponseEntity<?> updateEmployee(@RequestBody PersonInfo personInfo){
        personService.updateEmployee(personInfo);
        return ResponseEntity.ok("Информация успешно обновлена!");
    }


    @PostMapping("/delete/employee")
    public ResponseEntity<?> deleteEmployee(@RequestBody JsonNode body){
        Long employeeId = body.get("employeeId").asLong();
        personService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Успешно удалено!");
    }

    @PostMapping("/addManager")
    public ResponseEntity<?> addManager(@RequestBody JsonNode body){
        Long managerId = body.get("managerId").asLong();
        personService.addOrDeleteManager(managerId);
        return ResponseEntity.ok("Запись упешно добавлена!");
    }

    @PostMapping("/deleteManager")
    public ResponseEntity<?> deleteManager(){
        personService.addOrDeleteManager(null);
        return ResponseEntity.ok("Удалено успешно!");
    }
}
