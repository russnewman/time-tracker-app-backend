package com.example.TimeTracker.service;

import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;

public interface LogsService {
    public Site getSiteByUrl(Log log, Person person);
}
