package com.example.sso.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.sso.constants.ConstantsViews;

@ControllerAdvice
public class WebExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(WebExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView unexpectedExceptionHandler(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		ModelAndView mav = new ModelAndView(ConstantsViews.ERROR_500_VIEW);
		mav.addObject("error", exception.getMessage());
		return mav;
	}

}
