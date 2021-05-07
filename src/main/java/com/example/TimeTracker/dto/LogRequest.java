package com.example.TimeTracker.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LogRequest {
	@NotBlank
	private String browser;

	@NotBlank
	private String startDateTime;

	@NotBlank
	private String tabName;

	@NotBlank
	private String url;

	@NotBlank
	private Boolean background;

}
