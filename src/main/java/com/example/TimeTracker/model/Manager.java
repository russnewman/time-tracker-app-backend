package com.example.TimeTracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Manager {
    private Long id;
    private String email;
    private String fullName;
    private String department;
    private String position;
}
