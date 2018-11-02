package com.example.sso.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.DAO;
import com.example.sso.dao.VerificationTokenDAO;
import com.example.sso.model.User;
import com.example.sso.model.VerificationToken;
import com.example.sso.service.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl extends AbstractService<VerificationToken, Long>
		implements VerificationTokenService {

	private static final int EXPIRATION_MINUTES = 60 * 24;

	@Autowired
	private VerificationTokenDAO verificationTokenDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createVerificationToken(User user, String token) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setUser(user);
		verificationToken.setToken(token);
		verificationToken.setExpiryDate(calculateExpiryDate(EXPIRATION_MINUTES));
		save(verificationToken);
	}

	public LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
		return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
	}

	@Override
	protected DAO<VerificationToken, Long> getDAO() {
		return this.verificationTokenDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public VerificationToken findByToken(String token) {
		return this.verificationTokenDAO.findByToken(token);
	}

	@Override
	public boolean isTokenExpired(VerificationToken verificationToken) {
		return LocalDateTime.now().isAfter(verificationToken.getExpiryDate());
	}

}
