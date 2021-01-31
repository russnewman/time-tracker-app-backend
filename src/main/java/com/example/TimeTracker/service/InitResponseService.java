package com.example.TimeTracker.service;

import com.example.TimeTracker.model.Manager;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.payload.response.InitResponse;
import com.example.TimeTracker.payload.response.UserInfoResponse;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.security.services.UserDetailsImpl;
import com.example.TimeTracker.security.services.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitResponseService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PersonRepository personRepository;

    public InitResponse init(Authentication authentication) {
//        List<Person> employees = new ArrayList<>();

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserInfoResponse userInfoResponse = InitUserInfo(jwt, userDetails);

        if (userDetails.getUserRole().toString().equals("LEADER")) {
            List<Person> employees = personRepository.findAllByManagerId(userDetails.getId());
            return InitResponse.builder()
                    .userInfoResponse(userInfoResponse)
                    .employees(employees)
                    .build();
        }
        List<Manager> managers = personRepository.findAllManagers()
                .stream()
                .map(elem -> new Manager(
                        elem.getId(),
                        elem.getEmail(),
                        elem.getFullName(),
                        elem.getDepartment(),
                        elem.getPosition())
                )
                .collect(Collectors.toList());

        Long managerId = userDetails.getManagerId();
        Person manager = managerId == null ? null : personRepository.findById(managerId).orElseThrow();
        return InitResponse.builder()
                .userInfoResponse(userInfoResponse)
                .managers(managers)
                .userManager(manager)
                .build();
    }

    private UserInfoResponse InitUserInfo(String jwt, UserDetailsImpl userDetails) {
        String gender = userDetails.getGender() == null ? null : userDetails.getGender().toString();
        return new UserInfoResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFullName(),
                userDetails.getDepartment(),
                userDetails.getPosition(),
                userDetails.getUserRole().toString(),
                userDetails.getManagerId(),
                gender,
                userDetails.getHireDate()
        );
    }
}
