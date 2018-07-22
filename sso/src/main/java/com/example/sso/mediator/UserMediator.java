package com.example.sso.mediator;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.IDAO;
import com.example.sso.dao.IUserDAO;
import com.example.sso.dto.UserRegistrationFormDTO;
import com.example.sso.exception.EmailExistsException;
import com.example.sso.model.User;

@Service
public class UserMediator extends AbstractMediator<User, Long> implements IUserMediator {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IRoleMediator roleMediator;

	@Autowired
	private IUserDAO userDAO;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = this.userDAO.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(userNotFoundMessage(email));
		}

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, user.getAuthorities());

	}

	private String userNotFoundMessage(String email) {
		return this.messageSource.getMessage("user.not.found.message", ArrayUtils.toArray(email),
				LocaleContextHolder.getLocale());
	}

	@Override
	protected IDAO<User, Long> getDAO() {
		return this.userDAO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User registerUserAccount(UserRegistrationFormDTO form) {

		User user = null;

		try {
			user = createUserAccount(form);
		} catch (EmailExistsException ex) {
			return null;
		}

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
		user.setRoles(Arrays.asList(this.roleMediator.findByName("ROLE_USER")));

		return save(user);
	}

	private String emailExistsMessage(UserRegistrationFormDTO form) {
		return messageSource.getMessage("email.exists.on.registration.message", ArrayUtils.toArray(form.getEmail()),
				LocaleContextHolder.getLocale());
	}

	private boolean isEmailExists(String email) {
		return this.userDAO.userCountByFilter("email", email) > 0;
	}

}
