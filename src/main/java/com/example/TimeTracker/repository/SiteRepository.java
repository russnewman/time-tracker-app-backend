package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findByHostAndPerson(String host, Person person);
    List<Site> findAllByPerson(Person person);
//    ListSite findByResourceName(String url);
}
