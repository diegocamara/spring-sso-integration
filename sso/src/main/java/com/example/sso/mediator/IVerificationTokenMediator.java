package com.example.sso.mediator;

import com.example.sso.model.User;
import com.example.sso.model.VerificationToken;

public interface IVerificationTokenMediator {

	void createVerificationToken(User user, String token);

	VerificationToken findByToken(String token);

	boolean isTokenExpired(VerificationToken verificationToken);

}
