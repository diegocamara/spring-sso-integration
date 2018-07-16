package com.example.sso.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.UserDAO;
import com.example.sso.model.User;

@Service
public class UserMediator implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userDAO.consultByUsername(username);
	}

	@Transactional(readOnly = true)
	public User findByUserName(String userName) {
		return this.userDAO.findByUserName(userName);
	}

}
