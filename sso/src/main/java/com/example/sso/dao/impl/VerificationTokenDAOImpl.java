package com.example.sso.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.sso.dao.VerificationTokenDAO;
import com.example.sso.domain.VerificationToken;

@Repository
public class VerificationTokenDAOImpl extends AbstractDAO<VerificationToken, Long> implements VerificationTokenDAO {

	@Override
	public VerificationToken findByToken(String token) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<VerificationToken> criteriaQuery = getCriteriaQuery(builder);
		Root<VerificationToken> verificationToken = getRoot(criteriaQuery);
		criteriaQuery.select(verificationToken);
		criteriaQuery.where(builder.equal(verificationToken.get("token"), token));
		return getSingleResult(criteriaQuery);
	}

}
