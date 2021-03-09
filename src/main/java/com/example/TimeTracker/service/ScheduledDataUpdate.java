package com.example.TimeTracker.service;


import com.example.TimeTracker.model.Category;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Statistic;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class ScheduledDataUpdate {

    @Autowired
    LogsService logsService;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    PersonRepository personRepository;

    @Scheduled(cron = "59 59 23 * * *")
    public void updataData(){
        LocalDate currentDate = LocalDate.now();
        List<Person> users = personRepository.findAll();

        for (Person user: users){
            HashMap<Category, int[]> categoryHashMap = logsService.computeEfficiencyByUserAndDate(user.getId(), currentDate);
            Statistic statistic = new Statistic(user, currentDate,
                    categoryHashMap.get(Category.EFFECTIVE),
                    categoryHashMap.get(Category.NEUTRAL),
                    categoryHashMap.get(Category.INEFFECTIVE),
                    categoryHashMap.get(Category.WITHOUT));

            statisticRepository.save(statistic);

        }
    }
}
