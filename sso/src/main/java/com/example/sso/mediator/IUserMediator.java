package com.example.sso.mediator;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.sso.dto.UserRegistrationFormDTO;
import com.example.sso.model.User;

public interface IUserMediator extends UserDetailsService {

	User registerUserAccount(UserRegistrationFormDTO form);

}
