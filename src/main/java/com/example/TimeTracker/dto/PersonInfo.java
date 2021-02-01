package com.example.TimeTracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class PersonInfo {
	private Long id;
	private String email;
	private String fullName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String department;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String position;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String token;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String userRole;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long managerId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String gender;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date hireDate;
}
