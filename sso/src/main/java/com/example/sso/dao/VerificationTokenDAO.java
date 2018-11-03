package com.example.sso.dao;

import com.example.sso.domain.VerificationToken;

public interface VerificationTokenDAO extends DAO<VerificationToken, Long> {

	VerificationToken findByToken(String token);

}
