package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.LogKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogKeywordRepository extends JpaRepository<LogKeyword, Long> {

}