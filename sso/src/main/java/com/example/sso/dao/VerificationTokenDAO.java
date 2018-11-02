package com.example.sso.dao;

import com.example.sso.model.VerificationToken;

public interface VerificationTokenDAO extends DAO<VerificationToken, Long> {

	VerificationToken findByToken(String token);

}
