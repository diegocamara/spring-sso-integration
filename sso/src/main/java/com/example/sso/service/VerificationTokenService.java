package com.example.sso.service;

import com.example.sso.model.User;
import com.example.sso.model.VerificationToken;

public interface VerificationTokenService {

	void createVerificationToken(User user, String token);

	VerificationToken findByToken(String token);

	boolean isTokenExpired(VerificationToken verificationToken);

}
