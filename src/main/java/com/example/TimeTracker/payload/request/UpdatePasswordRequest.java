package com.example.TimeTracker.payload.request;
import lombok.Getter;


@Getter
public class UpdatePasswordRequest {
    private String email;
    private String password;
    private String newPassword;
}
