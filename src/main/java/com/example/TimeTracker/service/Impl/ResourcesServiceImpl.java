package com.example.TimeTracker.service.Impl;


import com.example.TimeTracker.dto.PeriodOfTime;
import com.example.TimeTracker.dto.Resource;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;
import com.example.TimeTracker.repository.LogsRepository;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.repository.SiteRepository;
import com.example.TimeTracker.service.LogsService;
import com.example.TimeTracker.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourcesServiceImpl implements ResourcesService {

    @Autowired
    LogsRepository logsRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    SiteRepository siteRepository;

    @Autowired
    LogsService logsService;


    @Override
    public Map<String, List<Resource>> getResourcesForAllTeam(Long userId, LocalDate date, PeriodOfTime periodOfTime) {

        List<Person> employees = personRepository.findAllByManagerId(userId);
        employees.add(personRepository.findById(userId).orElseThrow());

        HashMap<String, List<Resource>> result = new HashMap<>();

        employees.forEach(employee -> {
            getResourcesForEmployee(employee.getId(), date, periodOfTime).forEach(resource -> {

                if (result.get(resource.getHost()) != null) result.get(resource.getHost()).add(resource);
                else result.put(resource.getHost(), new ArrayList<>(){{
                    add(resource);
                }});
            });
        });
        return result;
    }

    @Override
    public List<Resource> getResourcesForEmployee(Long employeeId, LocalDate date, PeriodOfTime periodOfTime) {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(0, 0, 0));

        if (periodOfTime.equals(PeriodOfTime.DAY)) {

            LocalDateTime endOfTheDay = dateTime.plusDays(1).minusSeconds(1);
            return getListOfResources(employeeId, dateTime, endOfTheDay);
        }


        List<Resource> resourceList = new ArrayList<>();
        if (periodOfTime.equals(PeriodOfTime.WEEK)) {

            LocalDateTime beginWeek = dateTime.minusDays(dateTime.getDayOfWeek().getValue() - 1);
            LocalDateTime endWeek = beginWeek.plusDays(7).minusSeconds(1);
            resourceList = getListOfResources(employeeId, beginWeek, endWeek);
        } else if (periodOfTime.equals(PeriodOfTime.MOUNTH)) {

            LocalDateTime beginMonth = dateTime.minusDays(dateTime.getDayOfMonth() - 1);
            LocalDateTime endMonth = beginMonth.plusMonths(1).minusSeconds(1);
            resourceList = getListOfResources(employeeId, beginMonth, endMonth);
        }

        List<Resource> result = new ArrayList<>();
        HashMap<String, Resource> hostToResource = new HashMap<>();

        resourceList.forEach(resource -> {

            Resource resourceFromMap = hostToResource.get(resource.getHost());

            HashMap<String, String> urlToTabName = new HashMap<>(resource.getUrlToTabName());
            if (resourceFromMap != null) resourceFromMap.getUrlToTabName().forEach(urlToTabName::put);
            long duration = resourceFromMap == null ? 0L : resourceFromMap.getDuration();
            duration = ChronoUnit.SECONDS.between(resource.getStartTime(), resource.getEndTime()) + duration;

            Resource newResource = Resource.builder()
                    .category(resource.getCategory())
                    .employeeId(resource.getEmployeeId())
                    .host(resource.getHost())
                    .protocolIdentifier(resource.getProtocolIdentifier())
                    .urlToTabName(urlToTabName)
                    .duration(duration)
                    .build();
            hostToResource.put(resource.getHost(), newResource);

        });
        return getResultListFromMap(hostToResource);
    }


    private List<Resource> getListOfResources(Long employeeId, LocalDateTime start, LocalDateTime end) {
        List<Resource> result = new ArrayList<>();
        Person employee = personRepository.findById(employeeId).orElseThrow();
        List<Log> logsPerDay = logsRepository.findLogsByIdAndTwoPointsOfTime(employee.getId(), start, end);

        for (Log log : logsPerDay) {
            LocalDateTime startResourceTime = log.getStart().isBefore(start) ? start : log.getStart();
            LocalDateTime endResourceTime = log.getEnd().isAfter(end) ? end : log.getEnd();

            Site site = logsService.getSiteByUrl(log, employee);
            Resource resource = Resource.builder()
                    .employeeId(log.getUser().getId())
                    .host(Utils.extractResourceName(log.getUrl()))
                    .protocolIdentifier(Utils.extractProtocolIdentifier(log.getUrl()))
                    .category(site.getCategory())
                    .urlToTabName(new HashMap<>(){{put(log.getUrl(), log.getTabName());}})
                    .startTime(startResourceTime)
                    .endTime(endResourceTime)
                    .duration(ChronoUnit.SECONDS.between(startResourceTime, endResourceTime))
                    .build();
            result.add(resource);
        }
        return result;
    }


    private List<Resource> getResultListFromMap(HashMap<String,Resource> hostToResource){

        List<Resource> result = new ArrayList<>(hostToResource.values());

        return result.stream()
                .sorted((o1, o2) -> {
                    if (o1.getDuration() > o2.getDuration()) {
                        return -1;
                    } else if (o1.getDuration() < o2.getDuration()) {
                        return 1;
                    }
                    return 0;
                })
                .limit(100)
                .collect(Collectors.toList());
    }
}