package com.example.sso.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.sso.provider.SpringMessageProvider;

@ControllerAdvice
public class WebExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(WebExceptionHandler.class);

	@Autowired
	private SpringMessageProvider springMessageProvider;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView unexpectedExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		return new ModelAndView("error/error-500");
	}

	// @ExceptionHandler(paGEno)
	public ModelAndView notFoundException() {
		return null;
	}

}
