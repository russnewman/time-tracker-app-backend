package com.example.TimeTracker.service.Impl;

import com.example.TimeTracker.model.Category;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;
import com.example.TimeTracker.repository.LogsRepository;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.repository.SiteRepository;
import com.example.TimeTracker.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;


@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SiteRepository siteRepository;

    //TODO Case when log.getEnd can be null
    public HashMap<Category, int[]> computeEfficiencyByUserAndDate(Long userId, LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        HashMap<Category, int[]> result = new HashMap<>();

        LocalDateTime beginOfTheDay = getBeginOfTheDay(currentDate);
        LocalDateTime endOfTheDay = getEndOfTheDay(currentDate);

        Person person = personRepository.findById(userId).orElseThrow();
        List<Log> logsPerDay = logsRepository.findByUserAndStartBetweenOrEndBetween(person.getId(), beginOfTheDay, endOfTheDay);


        int[] effective = new int[24];
        int[] neutral = new int[24];
        int[] ineffective = new int[24];
        int[] without = new int[24];
        for (Log log : logsPerDay) {
            Site site = siteRepository.findByResourceNameAndPerson(extractResourceName(log.getUrl()), person)
                    .orElseGet(() -> {
                                Site site1 = Site.builder()
                                        .category(Category.WITHOUT)
                                        .resourceName(extractResourceName(log.getUrl()))
                                        .protocolIdentifier(extractProtocolIdentifier(log.getUrl()))
                                        .person(person)
                                        .build();
                                siteRepository.save(site1);
                                return site1;
                            }
                    );

            LocalDateTime beginLogDay = currentDate.getDayOfMonth() == log.getStart().getDayOfMonth() ? log.getStart()
                    : LocalDateTime.of(currentDate, LocalTime.of(0, 0, 0));

            LocalDateTime endLogDay = currentDate.getDayOfMonth() == log.getEnd().getDayOfMonth() ? log.getEnd()
                    : LocalDateTime.of(currentDate, LocalTime.of(23, 59, 59));

            LocalDateTime beginLogHour;
            LocalDateTime endLogHour;

            for (int h = beginLogDay.getHour(); h <= endLogDay.getHour(); h++) {

                beginLogHour = h == beginLogDay.getHour() ? beginLogDay :
                        LocalDateTime.of(currentDate, LocalTime.of(h, 0, 0));
                endLogHour = h == endLogDay.getHour() ? endLogDay :
                        LocalDateTime.of(currentDate, LocalTime.of(h, 59, 59));

                int diffSeconds = (int) ChronoUnit.SECONDS.between(beginLogHour, endLogHour);

                switch (site.getCategory()){
                    case WITHOUT:
                        without[h] += diffSeconds;
                        break;
                    case EFFECTIVE:
                        effective[h] += diffSeconds;
                        break;
                    case NEUTRAL:
                        neutral[h] += diffSeconds;
                        break;
                    case INEFFECTIVE:
                        ineffective[h] += diffSeconds;
                        break;
                }
            }
        }
        result.put(Category.WITHOUT, without);
        result.put(Category.EFFECTIVE, effective);
        result.put(Category.NEUTRAL, neutral);
        result.put(Category.INEFFECTIVE, ineffective);
        return result;
    }


    @Override
    public HashMap<Long, HashMap<Category, int[]>> computeEfficiencyAllTeam(Long userId, LocalDate date) {
        HashMap<Long, HashMap<Category, int[]>> result = new HashMap<>();
        List<Person> employees = personRepository.findAllByManagerId(userId);
        employees.forEach(
                (employee) ->{result.put(employee.getId(), computeEfficiencyByUserAndDate(employee.getId(), date));}
        );
        return result;
    }



    private String extractResourceName(String url) {
        try {
            URL url1 = new URL(url);
            return url1.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String extractProtocolIdentifier(String url) {
        try {
            URL url1 = new URL(url);
            return url1.getProtocol();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private LocalDateTime getBeginOfTheDay(LocalDate date) {
        return LocalDateTime.of(
                date.getYear(),
                date.getMonth(),
                date.getDayOfMonth(), 0, 0, 0);
    }

    private LocalDateTime getEndOfTheDay(LocalDate date) {
        return LocalDateTime.of(
                date.getYear(),
                date.getMonth(),
                date.getDayOfMonth(), 23, 59, 59);
    }
}
