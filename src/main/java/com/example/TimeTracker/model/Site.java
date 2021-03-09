package com.example.TimeTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sites")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "protocol_identifier")
    private String protocolIdentifier;

    @Column(name = "resource_name")
    private String resourceName;

    @Column
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person person;

//    @ManyToMany(mappedBy = "sites")
//    private List<Person> users;

}
