package com.example.TimeTracker.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
	private String token;
	private Long id;
	private String email;
	private String fullName;
	private String department;
	private String position;
	private String userRole;
	private String leaderEmail;
	private String gender;
	private Date hireDate;
}
