package com.example.sso.controller;

import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sso.constants.ConstantsViews;
import com.example.sso.util.ViewUtils;

@Controller
public class ErrorHandlerController implements ErrorController {

	@RequestMapping(ConstantsViews.DEFAULT_ERROR_PATH)
	public String handleError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Principal principal) {

		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {

				if (principal != null) {
					return ViewUtils.redirect(ConstantsViews.HOME_VIEW);
				}

				return ConstantsViews.ERROR_404_VIEW;
			}

			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return ConstantsViews.ERROR_500_VIEW;
			}

		}

		return ConstantsViews.DEFAULT_ERROR_VIEW;
	}

	@Override
	public String getErrorPath() {
		return ConstantsViews.DEFAULT_ERROR_PATH;
	}

}
