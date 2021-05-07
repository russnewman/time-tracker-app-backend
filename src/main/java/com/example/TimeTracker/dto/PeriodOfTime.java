package com.example.TimeTracker.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;


public enum PeriodOfTime {

    DAY("day"),
    WEEK("week"),
    MONTH("month");

    @Getter
    private String value;
    PeriodOfTime(String value){
        this.value = value;
    }

    @JsonCreator
    public static PeriodOfTime of(String value){
        return Arrays.stream(values()).filter(periodOfTime -> periodOfTime.getValue().equals(value)).findFirst().orElseThrow();
    }
}
