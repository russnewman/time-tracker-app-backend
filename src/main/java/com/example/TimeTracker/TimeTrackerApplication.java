package com.example.TimeTracker;

import com.example.TimeTracker.service.ScheduledDataUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TimeTrackerApplication {

//	@Autowired
//	ScheduledDataUpdate scheduledDataUpdate;
//	private static final int UPDATE_FOR_THE_LAST_NUMBER_DAYS = 4;

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackerApplication.class, args);
	}
}
