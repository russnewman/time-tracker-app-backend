package com.example.TimeTracker.payload.request;


//import com.example.TimeTracker.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.validation.constraints.NotBlank;
import java.sql.Date;


@Getter
@AllArgsConstructor
public class PersonInfo {

    private Long id;
    @NotBlank
    private String email;

    @NotBlank
    private String fullName;
    private String department;
    private String position;

    @NotBlank
    private String userRole;

    private Long managerId;
    private String gender;
    private Date hireDate;
}
