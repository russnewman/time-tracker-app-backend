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

    List<Log> findByUserAndStartDateTimeBetween(Person user, LocalDateTime start, LocalDateTime end);

    Log findFirstByUserAndStartDateTimeBeforeOrderByStartDateTimeDesc(Person user, LocalDateTime dateTime);
    Log findFirstByUserAndStartDateTime(Person user, LocalDateTime dateTime);

    @Query(value = "" +
            "SELECT * FROM logs l " +
            "WHERE l.user_id = ?1 " +
            "AND ((l.start_date_time between ?2 AND ?3) OR (l.end_date_time between ?2 AND ?3) OR (l.start_date_time < ?2 AND l.end_date_time > ?3))" +
            "ORDER BY l.start_date_time",
            nativeQuery = true)
    List<Log> findLogsByIdAndTwoPointsOfTime(Long id, LocalDateTime start, LocalDateTime end);


}