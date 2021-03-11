package com.example.TimeTracker.service;


import com.example.TimeTracker.model.Category;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Statistic;
import com.example.TimeTracker.repository.PersonRepository;
import com.example.TimeTracker.repository.StatisticRepository;
import com.example.TimeTracker.service.Impl.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class ScheduledDataUpdate {

    @Autowired
    EfficiencyService efficiencyService;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    PersonRepository personRepository;

    @Scheduled(cron = "59 59 23 * * *")
    public void updataData(){
        LocalDate currentDate = LocalDate.now();
        List<Person> users = personRepository.findAll();

        for (Person user: users){
            HashMap<Category, int[]> categoryHashMap = efficiencyService.computeEfficiencyByUserAndDate(user.getId(), currentDate);
            Statistic statistic = Statistic.builder()
                        .user(user)
                        .date(currentDate)
                        .effective(Utils.convertIntegersToBytes(categoryHashMap.get(Category.EFFECTIVE)))
                        .neutral(Utils.convertIntegersToBytes(categoryHashMap.get(Category.NEUTRAL)))
                        .ineffective(Utils.convertIntegersToBytes(categoryHashMap.get(Category.INEFFECTIVE)))
                        .without(Utils.convertIntegersToBytes(categoryHashMap.get(Category.WITHOUT)))
                        .build();
            statisticRepository.save(statistic);
        }
    }
}
