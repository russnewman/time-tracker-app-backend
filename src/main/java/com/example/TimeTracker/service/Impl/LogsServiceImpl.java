package com.example.TimeTracker.service.Impl;


import com.example.TimeTracker.model.Category;
import com.example.TimeTracker.model.Log;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.model.Site;
import com.example.TimeTracker.repository.LogsRepository;
import com.example.TimeTracker.repository.SiteRepository;
import com.example.TimeTracker.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    LogsRepository logsRepository;

    @Autowired
    SiteRepository siteRepository;


    public Site getSiteByUrl(Log log, Person person){
        return siteRepository.findByHostAndPerson(Utils.extractResourceName(log.getUrl()), person)
                .orElseGet(() -> {
                            Site site1 = Site.builder()
                                    .category(Category.WITHOUT)
                                    .host(Utils.extractResourceName(log.getUrl()))
                                    .protocolIdentifier(Utils.extractProtocolIdentifier(log.getUrl()))
                                    .person(person)
                                    .build();
                            siteRepository.save(site1);
                            return site1;
                        }
                );
    }
}
