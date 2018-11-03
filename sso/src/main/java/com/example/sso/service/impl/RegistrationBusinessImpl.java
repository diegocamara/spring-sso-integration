package com.example.sso.service.impl;

import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.domain.User;
import com.example.sso.domain.VerificationToken;
import com.example.sso.exception.EmailExistsException;
import com.example.sso.exception.InvalidVerificationTokenException;
import com.example.sso.exception.ExpiredTokenException;
import com.example.sso.model.dto.UserRegistrationFormDTO;
import com.example.sso.service.RegistrationBusiness;
import com.example.sso.service.RoleService;
import com.example.sso.service.UserService;
import com.example.sso.service.VerificationTokenService;

@Service
public class RegistrationBusinessImpl implements RegistrationBusiness {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User registerUserAccount(UserRegistrationFormDTO form) {

		User user = createUserAccount(form);
		String token = UUID.randomUUID().toString();
		user.setVerificationToken(this.verificationTokenService.createVerificationToken(user, token));

		return user;

	}

	private User createUserAccount(UserRegistrationFormDTO form) throws EmailExistsException {

		if (isEmailExists(form.getEmail())) {
			throw new EmailExistsException(emailExistsMessage(form));
		}

		User user = new User();
		user.setUsername(form.getUsername());
		user.setEmail(form.getEmail());
		user.setPassword(this.passwordEncoder.encode(form.getPassword()));
		user.setRoles(Arrays.asList(this.roleService.findByName("ROLE_USER")));

		return this.userService.save(user);
	}

	private String emailExistsMessage(UserRegistrationFormDTO form) {
		return messageSource.getMessage("email.exists.on.registration.message", ArrayUtils.toArray(form.getEmail()),
				LocaleContextHolder.getLocale());
	}

	private boolean isEmailExists(String email) {
		return this.userService.userCountByFilter("email", email) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void registrationConfirm(String token) {

		VerificationToken verificationToken = this.verificationTokenService.findByToken(token);

		if (verificationToken == null) {
			throw new InvalidVerificationTokenException();
		}

		if (this.verificationTokenService.isTokenExpired(verificationToken)) {
			this.verificationTokenService.delete(verificationToken);
			throw new ExpiredTokenException();
		}

		User user = verificationToken.getUser();
		user.setEnabled(true);

		this.userService.saveOrUpdate(user);

	}

}
