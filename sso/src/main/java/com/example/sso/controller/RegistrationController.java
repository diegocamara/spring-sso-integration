package com.example.sso.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.sso.constants.ConstantsMessages;
import com.example.sso.constants.ConstantsViews;
import com.example.sso.domain.User;
import com.example.sso.exception.ExpiredTokenException;
import com.example.sso.exception.InvalidVerificationTokenException;
import com.example.sso.model.dto.UserRegistrationFormDTO;
import com.example.sso.service.RegistrationBusiness;
import com.example.sso.util.ViewUtils;

@Controller
public class RegistrationController {

	private static final String USER_REGISTRATION_PATH = "/user/registration";
	private static final String USER_REGISTRATION_CONFIRM_PATH = "/user/registrationConfirm";

//	@Autowired
//	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private RegistrationBusiness registrationService;

	@GetMapping(USER_REGISTRATION_PATH)
	public String showRegistrationForm(WebRequest request, Model model) {
		model.addAttribute("form", new UserRegistrationFormDTO());
		return ConstantsViews.USER_REGISTRATION_VIEW;
	}

	@PostMapping(USER_REGISTRATION_PATH)
	public ModelAndView registerUserAccount(@ModelAttribute("form") @Valid UserRegistrationFormDTO form,
			BindingResult result, WebRequest request, Errors errors) {

		User user = null;

		if (!errors.hasErrors()) {
			user = this.registrationService.registerUserAccount(form);
		}

		if (user == null) {
			result.rejectValue("email", "message.regError");
		}

		if (result.hasErrors()) {
			return new ModelAndView(ConstantsViews.USER_REGISTRATION_VIEW, "form", form);
		}

		try {

//			String appUrl = request.getContextPath();
//			this.applicationEventPublisher
//					.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

		} catch (Exception ex) {
			return new ModelAndView(ConstantsViews.USER_REGISTRATION_EMAIL_ERROR_VIEW, "form", form);
		}

		return new ModelAndView(ConstantsViews.USER_REGISTRATION_SUCCESS_VIEW, "form", form);

	}

	@GetMapping(USER_REGISTRATION_CONFIRM_PATH)
	public String registrationConfirm(WebRequest request, Model model, @RequestParam String token) {
		Locale locale = request.getLocale();

		try {
			this.registrationService.registrationConfirm(token);
		} catch (InvalidVerificationTokenException invalidVerificationTokenException) {
			String message = this.messageSource.getMessage(ConstantsMessages.REGISTRATION_MESSAGE_INVALIDTOKEN, null,
					locale);
			model.addAttribute("message", message);
			return ViewUtils.redirect(ConstantsViews.USER_REGISTRATION_BADUSER_VIEW);
		} catch (ExpiredTokenException expiredTokenException) {
			String message = this.messageSource.getMessage(ConstantsMessages.REGISTRATION_MESSAGE_EXPIREDTOKEN, null,
					locale);
			model.addAttribute("message", message);
			return ViewUtils.redirect(ConstantsViews.USER_REGISTRATION_BADUSER_VIEW);
		}

		return ViewUtils.redirect(ConstantsViews.LOGIN_VIEW);
	}

}
