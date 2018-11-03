package com.example.sso.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.sso.dao.RoleDAO;
import com.example.sso.domain.Role;

@Repository
public class RoleDAOImpl extends AbstractDAO<Role, Long> implements RoleDAO {

	@Override
	public Role findByName(String roleName) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Role> criteriaQuery = getCriteriaQuery(builder);
		Root<Role> role = getRoot(criteriaQuery);
		criteriaQuery.select(role);
		criteriaQuery.where(builder.equal(role.get("name"), roleName));
		return getSingleResult(criteriaQuery);
	}

}
