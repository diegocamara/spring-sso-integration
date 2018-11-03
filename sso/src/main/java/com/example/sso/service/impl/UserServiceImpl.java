package com.example.sso.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.DAO;
import com.example.sso.dao.UserDAO;
import com.example.sso.domain.User;
import com.example.sso.service.UserService;

@Service
public class UserServiceImpl extends AbstractService<User, Long> implements UserService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserDAO userDAO;

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
	protected DAO<User, Long> getDAO() {
		return this.userDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public int userCountByFilter(String filterKey, String filterValue) {
		return this.userDAO.userCountByFilter(filterKey, filterValue);
	}

}
