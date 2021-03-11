package com.example.TimeTracker.service.Impl;

import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.model.*;
import com.example.TimeTracker.repository.LogsRepository;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.repository.SiteRepository;
import com.example.TimeTracker.repository.StatisticRepository;
import com.example.TimeTracker.service.EfficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Service
public class EfficiencyServiceImpl implements EfficiencyService {

    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    //TODO Case when log.getEnd can be null
    @Override
    public HashMap<Category, int[]> computeEfficiencyByUserAndDate(Long userId, LocalDate date) {

        //TODO Change this
//        LocalDate currentDate = LocalDate.now();
        HashMap<Category, int[]> result = new HashMap<>();
        Person person = personRepository.findById(userId).orElseThrow();

        if (date.equals(LocalDate.now())) {
            return computeEfficiencyByUserAndDateViaLogs(person, date);
        }
        else{
//            Statistic statistic = statisticRepository.findByUserAndDate(person, date)
//                    .orElse(Statistic.builder()
//                            .effective(new byte[0])
//                            .neutral(new byte[0])
//                            .ineffective(new byte[0])
//                            .without(new byte[0])
//                            .build());




            Statistic statistic = statisticRepository.findByUserAndDate(person, date)
                    .orElse(Statistic.builder()
                            .effective(new byte[0])
                            .neutral(new byte[0])
                            .ineffective(new byte[0])
                            .without(new byte[0])
                            .build());



            result.put(Category.WITHOUT, Utils.byte2int(statistic.getWithout()));
            result.put(Category.EFFECTIVE, Utils.byte2int(statistic.getWithout()));
            result.put(Category.NEUTRAL, Utils.byte2int(statistic.getWithout()));
            result.put(Category.INEFFECTIVE, Utils.byte2int(statistic.getWithout()));

            return result;
        }
    }





    private HashMap<Category, int[]> computeEfficiencyByUserAndDateViaLogs(Person person, LocalDate date){

        HashMap<Category, int[]> result = new HashMap<>();

        LocalDateTime beginOfTheDay = getBeginOfTheDay(date);
        LocalDateTime endOfTheDay = getEndOfTheDay(date);

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

            LocalDateTime beginLogDay = date.getDayOfMonth() == log.getStart().getDayOfMonth() ? log.getStart()
                    : LocalDateTime.of(date, LocalTime.of(0, 0, 0));

            LocalDateTime endLogDay = date.getDayOfMonth() == log.getEnd().getDayOfMonth() ? log.getEnd()
                    : LocalDateTime.of(date, LocalTime.of(23, 59, 59));

            LocalDateTime beginLogHour;
            LocalDateTime endLogHour;

            for (int h = beginLogDay.getHour(); h <= endLogDay.getHour(); h++) {

                beginLogHour = h == beginLogDay.getHour() ? beginLogDay :
                        LocalDateTime.of(date, LocalTime.of(h, 0, 0));
                endLogHour = h == endLogDay.getHour() ? endLogDay :
                        LocalDateTime.of(date, LocalTime.of(h, 59, 59));

                int diffSeconds = (int) ChronoUnit.SECONDS.between(beginLogHour, endLogHour);

                switch (site.getCategory()) {
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
    public HashMap<Long, HashMap<String, HashMap<Category, int[]>>> computeEfficiencyAllTeam(Long userId, LocalDate date, PeriodOfTime periodOfTime) {

        HashMap<Long, HashMap<String,HashMap<Category, int[]>>> result = new HashMap<>();
        List<Person> employees = personRepository.findAllByManagerId(userId);

        employees.forEach((employee) -> {
                    result.put(employee.getId(), computeEfficiencyEmployee(employee.getId(), date, periodOfTime));
                }
        );
        return result;

    }

    private HashMap<String, HashMap<Category, int[]>> computeEfficiencyEmployee(Long employeeId, LocalDate date, PeriodOfTime periodOfTime){
        HashMap<String, HashMap<Category, int[]>> result = new HashMap<>();
        HashMap<Category, int[]> efficiency;
        HashMap<Category, int[]> efficiencyPreviousPeriod;

        if (periodOfTime.equals(PeriodOfTime.DAY)){

            efficiency = computeEfficiencyByUserAndDate(employeeId, date);
            efficiencyPreviousPeriod = computeEfficiencyByUserAndDate(employeeId, date.minusDays(1));

        }
        else if (periodOfTime.equals(PeriodOfTime.WEEK)) {

            int dayOfWeek = date.getDayOfWeek().getValue();
            LocalDate beginWeek = date.minusDays(dayOfWeek - 1);
            LocalDate endWeek = beginWeek.plusDays(6);
            endWeek = endWeek.isBefore(LocalDate.now()) ? endWeek : LocalDate.now();

            efficiency = computeEfficiencyEmployee(employeeId, beginWeek, endWeek, 7);
            efficiencyPreviousPeriod =
                    computeEfficiencyEmployee(employeeId, beginWeek.minusWeeks(1), beginWeek.minusDays(1), 7);
        }
        else{
            LocalDate beginMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
            LocalDate endMonth = beginMonth.plusMonths(1).minusDays(1);
            int numberOfDaysInMonth = endMonth.getDayOfMonth();
            endMonth = endMonth.isBefore(LocalDate.now()) ? endMonth : LocalDate.now();

             efficiency =
                    computeEfficiencyEmployee(employeeId, beginMonth, endMonth, numberOfDaysInMonth);
             efficiencyPreviousPeriod =
                    computeEfficiencyEmployee(employeeId, beginMonth.minusMonths(1), beginMonth.minusDays(1), numberOfDaysInMonth);
        }
        result.put("Current", efficiency);
        result.put("Previous", efficiencyPreviousPeriod);
        return result;
    }

    private HashMap<Category, int[]> computeEfficiencyEmployee(Long employeeId, LocalDate beginDate, LocalDate endDate, int numberOfDays){
        HashMap<Category, int[]> result = new HashMap<>();

        int[] effectiveByDays = new int[numberOfDays];
        int[] neutralByDays = new int[numberOfDays];
        int[] ineffectiveByDays = new int[numberOfDays];
        int[] withoutByDays = new int[numberOfDays];

        LocalDate currentDate = beginDate;
        int currentDayOfWeek = 1;
        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)){
            HashMap<Category, int[]> efficiencyByUserAndDate = computeEfficiencyByUserAndDate(employeeId, currentDate);

            effectiveByDays[currentDayOfWeek-1] = Arrays.stream(efficiencyByUserAndDate.get(Category.EFFECTIVE)).sum();
            neutralByDays[currentDayOfWeek-1] = Arrays.stream(efficiencyByUserAndDate.get(Category.NEUTRAL)).sum();
            ineffectiveByDays[currentDayOfWeek-1] = Arrays.stream(efficiencyByUserAndDate.get(Category.INEFFECTIVE)).sum();
            withoutByDays[currentDayOfWeek-1] = Arrays.stream(efficiencyByUserAndDate.get(Category.WITHOUT)).sum();

            currentDate = currentDate.plusDays(1);
            ++currentDayOfWeek;
        }
        result.put(Category.WITHOUT, withoutByDays);
        result.put(Category.EFFECTIVE, effectiveByDays);
        result.put(Category.NEUTRAL, neutralByDays);
        result.put(Category.INEFFECTIVE, ineffectiveByDays);

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
