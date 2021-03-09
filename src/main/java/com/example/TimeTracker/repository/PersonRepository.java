package com.example.TimeTracker.repository;


import com.example.TimeTracker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findById(Long id);
    Optional<Person> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<Person> findAllByManagerId(Long managerId);

    @Query(value = "SELECT * FROM logs_user u WHERE u.role = 0",
            nativeQuery = true)
    List<Person> findAllManagers();

}