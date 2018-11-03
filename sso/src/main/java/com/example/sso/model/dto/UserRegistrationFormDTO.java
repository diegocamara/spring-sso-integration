package com.example.sso.model.dto;

import javax.validation.constraints.NotBlank;

import com.example.sso.annotation.PasswordMatches;
import com.example.sso.annotation.ValidEmail;

@PasswordMatches
public class UserRegistrationFormDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String matchingPassword;

	@NotBlank
	@ValidEmail
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
