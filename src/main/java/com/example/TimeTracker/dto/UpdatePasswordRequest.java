package com.example.TimeTracker.dto;
import lombok.Getter;


@Getter
public class UpdatePasswordRequest {
    private String email;
    private String password;
    private String newPassword;
}
