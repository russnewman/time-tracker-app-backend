package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

}
