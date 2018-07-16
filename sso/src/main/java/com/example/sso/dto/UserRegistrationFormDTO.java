package com.example.sso.dto;

import javax.validation.constraints.NotBlank;

import com.example.sso.annotation.PasswordMatches;
import com.example.sso.annotation.ValidEmail;

@PasswordMatches
public class UserRegistrationFormDTO {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String password;

	@NotBlank
	private String matchingPassword;

	@NotBlank
	@ValidEmail
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
