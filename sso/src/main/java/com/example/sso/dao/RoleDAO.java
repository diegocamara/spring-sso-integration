package com.example.sso.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.sso.model.Role;

@Repository
public class RoleDAO extends AbstractDAO<Role, Long> implements IRoleDAO {

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
