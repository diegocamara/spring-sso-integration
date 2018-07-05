package com.example.sso.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringMessageProvider {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String key, String... args) {
		return this.messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
	}

}
