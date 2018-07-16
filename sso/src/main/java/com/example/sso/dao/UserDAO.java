package com.example.sso.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.sso.model.User;

@Repository
public class UserDAO extends AbstractDAO<User, Long> implements IUserDAO {

	@Override
	public User consultByUsername(String username) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = getCriteriaQuery(builder);
		Root<User> user = getRoot(criteriaQuery);
		criteriaQuery.select(user);
		criteriaQuery.where(builder.like(builder.upper(user.get("username")), username.toUpperCase()));
		return getSingleResult(criteriaQuery);
	}

	@Override
	public int userCountByFilter(String filterKey, String filterValue) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = getCriteriaQuery(builder, Long.class);
		Root<User> user = getRoot(criteriaQuery, User.class);
		criteriaQuery.select(builder.count(user));
		criteriaQuery.where(builder.equal(user.get(filterKey), filterValue));
		return getSingleResult(criteriaQuery).intValue();
	}

	@Override
	public User findByEmail(String email) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = getCriteriaQuery(builder);
		Root<User> user = getRoot(criteriaQuery);
		criteriaQuery.select(user);
		criteriaQuery.where(builder.equal(user.get("email"), email));
		return getSingleResult(criteriaQuery);
	}

}
