package com.example.sso.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.sso.dto.UserRegistrationFormDTO;
import com.example.sso.model.User;

public interface UserService extends UserDetailsService, Service<User, Long>, ICRUDService<User, Long> {

	User registerUserAccount(UserRegistrationFormDTO form);

}
