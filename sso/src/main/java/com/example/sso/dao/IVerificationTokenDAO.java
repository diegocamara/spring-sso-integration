package com.example.sso.dao;

import com.example.sso.model.VerificationToken;

public interface IVerificationTokenDAO extends IDAO<VerificationToken, Long> {

	VerificationToken findByToken(String token);

}
