package com.example.sso.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.example.sso.model.VerificationToken;

@Repository
public class VerificationTokenDAO extends AbstractDAO<VerificationToken, Long> implements IVerificationTokenDAO {

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
