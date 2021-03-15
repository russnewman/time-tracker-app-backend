package com.example.TimeTracker.model;

import lombok.Getter;

import java.util.Arrays;

public enum Category {
    EFFECTIVE("effective"),
    NEUTRAL("neutral"),
    INEFFECTIVE("ineffective"),
    WITHOUT("without");

    @Getter
    private final String value;

    Category(String value){
        this.value = value;
    }

    public static Category of(String value){
        return Arrays.stream(values()).filter(category -> category.getValue().equals(value)).findFirst().orElseThrow();
    }

}
