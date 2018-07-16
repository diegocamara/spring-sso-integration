package com.example.sso.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	public User findByUserName(String userName) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = getCriteriaQuery(builder);
		Root<User> user = getRoot(criteriaQuery);
		criteriaQuery.select(user);
		criteriaQuery.where(builder.like(builder.upper(user.get("username")), userName.toUpperCase() + "%"));
		return getSingleResult(criteriaQuery);
	}

}
