package com.example.TimeTracker.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="logs", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "startDateTime"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String browser;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Person user;

    @Column(nullable=false)
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    @Column(nullable=false, columnDefinition="TEXT")
    private String tabName;

    @Column(nullable=false, columnDefinition="TEXT")
    private String url;
}
