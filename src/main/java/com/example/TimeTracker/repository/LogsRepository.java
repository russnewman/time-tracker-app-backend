package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Log, Long> {

    List<Log> findByUserAndStartBetween(Person user, LocalDateTime start, LocalDateTime end);


    @Query(value = "" +
            "SELECT * FROM logs_log l " +
            "WHERE l.user_id = ?1 " +
            "AND ((l.start between ?2 AND ?3) OR (l.end between ?2 AND ?3) OR (l.start < ?2 AND l.end > ?3))" +
            "ORDER BY l.start",
            nativeQuery = true)
    List<Log> findLogsByIdAndTwoPointsOfTime(Long id, LocalDateTime start, LocalDateTime end);


}