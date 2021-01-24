package com.example.TimeTracker.payload.response;


import com.example.TimeTracker.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private String firstName = "";
	private String lastName = "";
	private String userRole;
	private String leaderEmail = "";


	public JwtResponse(String accessToken,
					   Long id,
					   String email,
					   String firstName,
					   String lastName,
					   String userRole,
					   String leaderEmail) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.leaderEmail = leaderEmail;
	}
}
