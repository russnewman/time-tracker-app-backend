package com.example.TimeTracker.security.services;

import com.example.TimeTracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private PersonRepository personRepository;

    public Long getUserIdFromContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        return personRepository.findByEmail(userDetails.getUsername()).orElseThrow().getId();
    }
}
