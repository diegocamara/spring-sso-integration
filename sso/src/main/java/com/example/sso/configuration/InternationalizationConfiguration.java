package com.example.sso.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InternationalizationConfiguration {

	private static final String DEFAULT_ENCODE = "UTF-8";
	private static final String[] RESOURCES_CLASSPATH = { "classpath:i18n/messages" };

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {		
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(RESOURCES_CLASSPATH);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding(DEFAULT_ENCODE);
		messageSource.setCacheSeconds(5);
		return messageSource;
	}

}
