package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.LogMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMetaRepository extends JpaRepository<LogMeta, Long> {

}