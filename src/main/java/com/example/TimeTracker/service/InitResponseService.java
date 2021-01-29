package com.example.TimeTracker.service;

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

@Service
public class InitResponseService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PersonRepository personRepository;

    public InitResponse init(Authentication authentication){
        List<Person> employees = new ArrayList<>();

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserInfoResponse userInfoResponse = InitUserInfo(jwt, userDetails);
        if(userDetails.getUserRole().toString().equals("LEADER")){
            employees = personRepository.findAllByLeaderEmail(userDetails.getEmail());
        }
        return new InitResponse(userInfoResponse, employees);
    }

    private UserInfoResponse InitUserInfo(String jwt, UserDetailsImpl userDetails){
        String gender = userDetails.getGender() == null ? null : userDetails.getGender().toString();
        return  new UserInfoResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFullName(),
                userDetails.getDepartment(),
                userDetails.getPosition(),
                userDetails.getUserRole().toString(),
                userDetails.getLeaderEmail(),
                gender,
                userDetails.getHireDate()
        );
    }

//    private UserEmployeesResponse InitEmployees(UserDetailsImpl userDetails){
//        if(userDetails.getUserRole().toString().equals("LEADER")){
//            List<User> employees = userRepository.findAllByLeaderEmail(userDetails.getEmail());
//            return new UserEmployeesResponse(employees);
//        }
//        return new UserEmployeesResponse();
//    }
}
