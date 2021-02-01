package com.example.TimeTracker.dto;

import com.example.TimeTracker.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String fullName;

    private String department;

    private String position;

    private String role;


}
