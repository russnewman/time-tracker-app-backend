package com.example.TimeTracker.controller;

import com.example.TimeTracker.dto.LogRequest;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.repository.LogsRepository;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.security.services.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/log")
@CrossOrigin(origins="*")
public class LogController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LogsRepository logsRepository;

    @Autowired
    PersonRepository personRepository;

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);


    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLog(@RequestBody LogRequest logRequest, @RequestHeader(value = "Authorization") String auth) {

        String token = auth.replace("Bearer ", "");
        if (!jwtUtils.validateJwtToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String email = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Person> user_optional = personRepository.findByEmail(email);
        if (user_optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDateTime = LocalDateTime.parse(logRequest.getStartDateTime(), formatter);

        Log log = Log.builder()
            .user(user_optional.get())
            .browser(logRequest.getBrowser())
            .startDateTime(startDateTime)
            .tabName(logRequest.getTabName())
            .url(logRequest.getUrl())
            .background(logRequest.getBackground())
            .build();

        logsRepository.save(log);

        Log prevLog = logsRepository
                .findFirstByUserAndStartDateTimeBeforeOrderByStartDateTimeDesc(user_optional.get(), startDateTime);

        if (prevLog != null) {
            prevLog.setEndDateTime(startDateTime);
            logsRepository.save(prevLog);
        }
        return ResponseEntity.ok("");
    }
}
