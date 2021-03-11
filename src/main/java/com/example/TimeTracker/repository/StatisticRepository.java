package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
        Optional<Statistic> findByUserAndDate(Person person, LocalDate date);
}
