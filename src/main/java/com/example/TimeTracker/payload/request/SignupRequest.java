package com.example.TimeTracker.payload.request;

import com.example.TimeTracker.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignupRequest {

    private String firstName;

    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String department;

    private String role;


}
