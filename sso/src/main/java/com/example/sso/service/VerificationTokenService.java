package com.example.sso.service;

import com.example.sso.domain.User;
import com.example.sso.domain.VerificationToken;

public interface VerificationTokenService {

	void createVerificationToken(User user, String token);

	VerificationToken findByToken(String token);

	boolean isTokenExpired(VerificationToken verificationToken);

}
