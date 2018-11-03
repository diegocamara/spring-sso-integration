package com.example.sso.service;

import com.example.sso.domain.User;
import com.example.sso.model.dto.UserRegistrationFormDTO;

public interface RegistrationBusiness {

	User registerUserAccount(UserRegistrationFormDTO form);

	void registrationConfirm(String token);

}
