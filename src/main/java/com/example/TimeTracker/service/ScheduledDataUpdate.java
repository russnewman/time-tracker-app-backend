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

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class ScheduledDataUpdate {

    private static final int UPDATE_FOR_THE_LAST_NUMBER_DAYS = 4;

    @Autowired
    private  EfficiencyService efficiencyService;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private  PersonRepository personRepository;

    @Scheduled(cron = "59 59 23 * * *")
    private void updateData(){
        LocalDate date = LocalDate.now();
        computeAndSaveEfficiencyForAllUsers(date);
    }

//    @PostConstruct
//    public void updateDataInit(){
//        LocalDate beginDate = LocalDate.now().minusDays(UPDATE_FOR_THE_LAST_NUMBER_DAYS);
//        LocalDate currentDate = beginDate;
//
//        while (currentDate.isBefore(LocalDate.now()) || currentDate.isEqual(LocalDate.now())){
//            computeAndSaveEfficiencyForAllUsers(currentDate);
//            currentDate = currentDate.plusDays(1);
//        }
//    }


    private void computeAndSaveEfficiencyForAllUsers(LocalDate date) {

        List<Person> users = personRepository.findAll();
        for (Person user : users) {
            HashMap<Category, int[]> categoryHashMap = efficiencyService.computeEfficiencyByUserAndDateViaLogs(user, date);
            Statistic statistic = Statistic.builder()
                    .user(user)
                    .date(date)
                    .effective(Utils.convertIntegersToBytes(categoryHashMap.get(Category.EFFECTIVE)))
                    .neutral(Utils.convertIntegersToBytes(categoryHashMap.get(Category.NEUTRAL)))
                    .ineffective(Utils.convertIntegersToBytes(categoryHashMap.get(Category.INEFFECTIVE)))
                    .without(Utils.convertIntegersToBytes(categoryHashMap.get(Category.WITHOUT)))
                    .build();
            statisticRepository.save(statistic);
        }
    }
}
