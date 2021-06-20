package com.example.TimeTracker.controller;

import com.example.TimeTracker.dto.LogMetaRequest;
import com.example.TimeTracker.dto.LogRequest;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.LogKeyword;
import com.example.TimeTracker.model.LogMeta;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.repository.LogKeywordRepository;
import com.example.TimeTracker.repository.LogMetaRepository;
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
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/log")
@CrossOrigin(origins="*")
public class LogController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private LogMetaRepository logMetaRepository;

    @Autowired
    private LogKeywordRepository logKeywordRepository;

    @Autowired
    private PersonRepository personRepository;

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
            return ResponseEntity.status(e.getStatusCode()).build();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDateTime = LocalDateTime.parse(logRequest.getStartDateTime(), formatter);

        Log logExists = logsRepository.findFirstByUserAndStartDateTime(user, startDateTime);
        if (logExists != null) {
            assert logExists.getUrl().equals(logRequest.getUrl());
            return ResponseEntity.ok().build();
        }

        Log log = Log.builder()
                .user(user)
                .browser(logRequest.getBrowser())
                .startDateTime(startDateTime)
                .tabName(logRequest.getTabName())
                .url(logRequest.getUrl())
                .background(logRequest.getBackground())
                .build();

        Log savedLog = logsRepository.save(log);
        return ResponseEntity.ok().body(savedLog.getId());
    }

    @PostMapping(value = "/setLastLogEndDateTime", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setLastLogEndDateTime(@RequestBody String datetime, @RequestHeader(value = "Authorization") String auth) {

        Person user;
        try {
            user = authorize(auth);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime endDateTime = LocalDateTime.parse(datetime, formatter);

        Log log = logsRepository
                .findFirstByUserAndStartDateTimeBeforeOrderByStartDateTimeDesc(user, endDateTime);

        if (log != null && log.getEndDateTime() == null) {
            log.setEndDateTime(endDateTime);
            logsRepository.save(log);
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/setMetas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setMetas(@RequestBody LogMetaRequest logMetaRequest, @RequestHeader(value = "Authorization") String auth) {

        Person user;
        try {
            user = authorize(auth);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }

        Log log;
        try {
            log = logsRepository.getOne(logMetaRequest.getLogId());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Log with such ID does not exist");
        }

        if (log.getUser() != user) {
            return ResponseEntity.badRequest().body("This user does not have log with such ID");
        }

        for (LogMetaRequest.Meta meta : logMetaRequest.getMetas()) {
            LogMeta logMeta = LogMeta.builder()
                    .log(log)
                    .name(meta.getName())
                    .property(meta.getProperty())
                    .content(meta.getContent())
                    .build();

            logMetaRepository.save(logMeta);

            if (meta.getName() != null && meta.getName().equals("keywords")) {
                for (String keyword : meta.getContent().split(", ")) {
                    LogKeyword logKeyword = LogKeyword.builder()
                            .log(log)
                            .keyword(keyword)
                            .build();
                    logKeywordRepository.save(logKeyword);
                }
            }
        }

        return ResponseEntity.ok().build();
    }
}
