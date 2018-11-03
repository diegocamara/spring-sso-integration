package com.example.sso.service;

import com.example.sso.domain.User;
import com.example.sso.domain.VerificationToken;

public interface VerificationTokenService
		extends Service<VerificationToken, Long>, ICRUDService<VerificationToken, Long> {

	VerificationToken createVerificationToken(User user, String token);

	VerificationToken findByToken(String token);

	boolean isTokenExpired(VerificationToken verificationToken);

}
