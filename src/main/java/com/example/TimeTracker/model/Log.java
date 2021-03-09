package com.example.TimeTracker.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="logs_log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String browser;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Person user;

    @Column
    private LocalDateTime start;

    @Column
    private LocalDateTime end;

    @Column(name="tab_name")
    private String tabName;

    @Column
    private String url;

    @Column
    private boolean background;
}
