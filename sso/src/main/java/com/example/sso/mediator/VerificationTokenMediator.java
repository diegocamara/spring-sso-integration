package com.example.sso.mediator;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.IDAO;
import com.example.sso.dao.IVerificationTokenDAO;
import com.example.sso.model.User;
import com.example.sso.model.VerificationToken;

@Service
public class VerificationTokenMediator extends AbstractMediator<VerificationToken, Long>
		implements IVerificationTokenMediator {

	private static final int EXPIRATION_MINUTES = 60 * 24;

	@Autowired
	private IVerificationTokenDAO verificationTokenDAO;

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
	protected IDAO<VerificationToken, Long> getDAO() {
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
