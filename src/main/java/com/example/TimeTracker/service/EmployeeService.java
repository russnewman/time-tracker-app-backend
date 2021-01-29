package com.example.TimeTracker.service;


import com.example.TimeTracker.payload.request.PersonInfo;
import com.example.TimeTracker.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    PersonRepository personRepository;

    public void updateEmployee(PersonInfo personInfo){
        personInfo.getEmail();
    }
}
