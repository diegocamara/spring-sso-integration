package com.example.sso.exception;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.sso.provider.ConstantsMessages;
import com.example.sso.provider.SpringMessageProvider;

@ControllerAdvice
public class WebExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(WebExceptionHandler.class);

	@Autowired
	private SpringMessageProvider springMessageProvider;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DefaultErrorMessage unexpectedExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		List<String> errors = Arrays.asList(this.springMessageProvider.getMessage(ConstantsMessages.UNEXPECTED_ERROR));
		return new DefaultErrorMessage("UNEXPECTED_EXCEPTION", HttpStatus.BAD_REQUEST.name(), errors);
	}

}
