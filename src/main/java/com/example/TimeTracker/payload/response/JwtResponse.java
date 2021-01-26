package com.example.TimeTracker.payload.response;


import com.example.TimeTracker.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
	private String token;
//	private String type = "Bearer";
	private Long id;
	private String email;
	private String fullName;
	private String department;
	private String position;
	private String userRole;
	private String leaderEmail;
	private String gender;
	private Date hireDate;


//	public JwtResponse(String accessToken,
//					   Long id,
//					   String email,
//					   String fullName,
//					   String department,
//					   String position,
//					   String userRole,
//					   String leaderEmail,
//					   String gender,
//					   Date hireDate
//					   ) {
//		this.token = accessToken;
//		this.id = id;
//		this.email = email;
//		this.fullName = fullName;
//		this.department = department;
//		this.userRole = userRole;
//		this.leaderEmail = leaderEmail;
//		this.position = position;
//		this.gender = gender;
//		this.hireDate = hireDate;
//
//	}
}
