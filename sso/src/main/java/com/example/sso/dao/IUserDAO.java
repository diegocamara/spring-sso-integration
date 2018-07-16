package com.example.sso.dao;

import com.example.sso.model.User;

public interface IUserDAO extends IDAO<User, Long> {

	User consultByUsername(String username);

	int userCountByFilter(String filterKey, String filterValue);

	User findByEmail(String email);

}
