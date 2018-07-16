package com.example.sso.dao;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.sso.model.User;

public interface IUserDAO extends IDAO<User, Long> {

	UserDetails consultByUsername(String username);

	int userCountByFilter(String filterKey, String filterValue);

}
