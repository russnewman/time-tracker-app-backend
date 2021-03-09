package com.example.TimeTracker.service;

import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.dto.InitResponse;
import com.example.TimeTracker.dto.PersonInfo;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.security.services.UserDetailsImpl;
import com.example.TimeTracker.security.services.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitResponseService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PersonRepository personRepository;
//    @Autowired
//    LogsRepository logsRepository;
//    @Autowired
//    EmployeeRepository employeeRepository;

    public InitResponse init(Authentication authentication) {
//        List<Person> employees = new ArrayList<>();

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        PersonInfo personInfo = InitUserInfo(jwt, userDetails);

        if (userDetails.getUserRole().toString().equals("LEADER")) {
            List<PersonInfo> employees = personRepository.findAllByManagerId(userDetails.getId())
                    .stream()
                    .map(Person::toPersonInfo)
                    .collect(Collectors.toList());

            return InitResponse.builder()
                    .personInfo(personInfo)
                    .employees(employees)
                    .build();
        }
        List<PersonInfo> managers = personRepository.findAllManagers()
                .stream()
                .map(Person::toPersonInfo)
                .collect(Collectors.toList());

        Long managerId = userDetails.getManagerId();
        PersonInfo manager = managerId == null ? null : personRepository.findById(managerId).orElseThrow().toPersonInfo();
        return InitResponse.builder()
                .personInfo(personInfo)
                .managers(managers)
                .userManager(manager)
                .build();
    }

    private PersonInfo InitUserInfo(String jwt, UserDetailsImpl userDetails) {
        String gender = userDetails.getGender() == null ? null : userDetails.getGender().toString();
//        System.out.println("SDASDA");
//        System.out.println(logsRepository.findByUser(employeeRepository.findByNickname("user")));
//        System.out.println("SDASDA");
//        System.out.println(employeeRepository.findByNickname("user").getLogs());
//        System.out.println(logsRepository.findById(2L).get().getStart());
//        System.out.println(logsRepository.findById(2L).get().getTabName());

        return  PersonInfo
                .builder()
                .token(jwt)
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .fullName(userDetails.getFullName())
                .department(userDetails.getDepartment())
                .position(userDetails.getPosition())
                .userRole(userDetails.getUserRole().toString())
                .managerId(userDetails.getManagerId())
                .gender(gender)
                .hireDate(userDetails.getHireDate())
                .build();
    }
}
