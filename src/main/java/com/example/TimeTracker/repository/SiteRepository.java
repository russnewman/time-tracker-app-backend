package com.example.TimeTracker.repository;

import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findByResourceNameAndPerson(String resourceName, Person person);
//    ListSite findByResourceName(String url);
}
