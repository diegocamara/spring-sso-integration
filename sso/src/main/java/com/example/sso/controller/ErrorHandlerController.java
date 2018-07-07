package com.example.sso.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandlerController implements ErrorController {

	private static final String DEFAULT_ERROR_VIEW = "errors/error";
	private static final String ERROR_500_VIEW = "errors/error-500";
	private static final String ERROR_404_VIEW = "errors/error-404";
	private static final String DEFAULT_ERROR_PATH = "/error";

	@RequestMapping(DEFAULT_ERROR_PATH)
	public String handleError(HttpServletRequest httpServletRequest) {

		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return ERROR_404_VIEW;
			}

			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return ERROR_500_VIEW;
			}

		}

		return DEFAULT_ERROR_VIEW;
	}

	@Override
	public String getErrorPath() {
		return DEFAULT_ERROR_PATH;
	}

}
