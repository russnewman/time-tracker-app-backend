package com.example.TimeTracker.model;

import com.example.TimeTracker.dto.PersonInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;


@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Data
@NoArgsConstructor
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

    @NotNull
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

    public PersonInfo toPersonInfo(){
        return PersonInfo.builder()
                .id(this.getId())
                .email(this.getEmail())
                .fullName(this.getFullName())
                .department(this.getDepartment())
                .position(this.getPosition())
                .gender(this.getGender() == null ? null : this.getGender().toString())
                .managerId(this.getManagerId())
                .hireDate(this.getHireDate())
                .userRole(this.getRole().toString())
                .build();
    }
}
