package com.example.TimeTracker.controller;

import javax.validation.Valid;
import com.example.TimeTracker.model.UserRole;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.payload.request.LoginRequest;
import com.example.TimeTracker.payload.request.SignupRequest;
import com.example.TimeTracker.payload.response.MessageResponse;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.security.services.jwt.JwtUtils;
import com.example.TimeTracker.service.InitResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    InitResponseService initResponseService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        return ResponseEntity.ok(initResponseService.init(authentication));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (personRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserRole userRole = signUpRequest.getRole().equals("leader") ? UserRole.LEADER : UserRole.EMPLOYEE;
        Person person = new Person(
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFullName(),
                signUpRequest.getDepartment(),
                signUpRequest.getPosition(),
                userRole
        );

        personRepository.save(person);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

        return ResponseEntity.ok(initResponseService.init(authentication));

//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
