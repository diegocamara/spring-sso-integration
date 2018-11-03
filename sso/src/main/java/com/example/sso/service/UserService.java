package com.example.sso.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.sso.domain.User;
import com.example.sso.model.dto.UserRegistrationFormDTO;

public interface UserService extends UserDetailsService, Service<User, Long>, ICRUDService<User, Long> {

	User registerUserAccount(UserRegistrationFormDTO form);

}
