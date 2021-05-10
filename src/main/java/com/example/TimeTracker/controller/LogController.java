package com.example.TimeTracker.controller;

import com.example.TimeTracker.dto.LogRequest;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.repository.LogsRepository;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.security.services.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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

    private Person authorize(String auth) throws HttpClientErrorException {
        String token = auth.replace("Bearer ", "");
        if (!jwtUtils.validateJwtToken(token)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        String email = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Person> user_optional = personRepository.findByEmail(email);
        if (user_optional.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return user_optional.get();
    };

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLog(@RequestBody LogRequest logRequest, @RequestHeader(value = "Authorization") String auth) {

        Person user;
        try {
            user = authorize(auth);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDateTime = LocalDateTime.parse(logRequest.getStartDateTime(), formatter);

        Log log = Log.builder()
                .user(user)
                .browser(logRequest.getBrowser())
                .startDateTime(startDateTime)
                .tabName(logRequest.getTabName())
                .url(logRequest.getUrl())
                .background(logRequest.getBackground())
                .build();

        logsRepository.save(log);
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/setLastLogEndDateTime", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setLastLogEndDateTime(@RequestBody String datetime, @RequestHeader(value = "Authorization") String auth) {

        Person user;
        try {
            user = authorize(auth);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime endDateTime = LocalDateTime.parse(datetime, formatter);

        Log log = logsRepository
                .findFirstByUserAndStartDateTimeBeforeOrderByStartDateTimeDesc(user, endDateTime);

        if (log != null && log.getEndDateTime() == null) {
            log.setEndDateTime(endDateTime);
            logsRepository.save(log);
        }
        return ResponseEntity.ok("");
    }
}
