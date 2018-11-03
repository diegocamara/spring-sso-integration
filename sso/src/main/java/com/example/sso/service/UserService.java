package com.example.sso.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.sso.domain.User;

public interface UserService extends UserDetailsService, Service<User, Long>, ICRUDService<User, Long> {

	int userCountByFilter(String filterKey, String filterValue);

}
