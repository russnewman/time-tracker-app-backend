package com.example.TimeTracker.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;


@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    private String fullName;

    private String department;

    private String position;

    @NotBlank
    private UserRole role;

    private Long managerId;
    private Gender gender;
    private Date hireDate;




    public Person(String email,
                  String password,
                  String fullName,
                  String department,
                  String position,
                  UserRole role) {


        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.role = role;
    }
}
