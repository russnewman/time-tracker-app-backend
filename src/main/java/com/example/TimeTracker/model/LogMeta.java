package com.example.TimeTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="log_id", nullable=false)
    private Log log;

    @Column()
    private String name;

    @Column()
    private String property;

    @Column(nullable=false, columnDefinition="TEXT")
    private String content;

}
