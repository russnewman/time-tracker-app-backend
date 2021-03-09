package com.example.TimeTracker;


import java.time.LocalDateTime;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(date.getTime());
        System.out.println(localDateTime);
    }
}
