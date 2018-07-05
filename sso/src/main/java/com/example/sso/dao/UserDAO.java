package com.example.sso.dao;

import org.hibernate.Criteria;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.sso.model.User;

@Repository
public class UserDAO extends AbstractHibernateDAO<User, Long> {

	public UserDetails consultByUsername(String username) {
		Criteria criteria = createCriteria();
		return (User) criteria.uniqueResult();
	}

}
