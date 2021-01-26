package com.example.TimeTracker.payload.request;


import com.example.TimeTracker.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.validation.constraints.NotBlank;
import java.sql.Date;


@Getter
@AllArgsConstructor
public class UpdateRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;
    private String department;
    private String position;

    @NotBlank
    private String userRole;

    private String leaderEmail;
    private String gender;
    private Date hireDate;
}
