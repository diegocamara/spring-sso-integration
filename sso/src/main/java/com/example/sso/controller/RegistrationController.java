package com.example.sso.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.example.sso.constants.ConstantsViews;
import com.example.sso.dto.UserRegistrationFormDTO;
import com.example.sso.event.OnRegistrationCompleteEvent;
import com.example.sso.model.User;
import com.example.sso.model.VerificationToken;
import com.example.sso.service.UserService;
import com.example.sso.service.VerificationTokenService;
import com.example.sso.util.ViewUtils;

@Controller
public class RegistrationController {

	private static final String USER_REGISTRATION_PATH = "/user/registration";
	private static final String USER_REGISTRATION_CONFIRM_PATH = "/user/registrationConfirm";

	@Autowired
	private UserService userMediator;

	@Autowired
	private VerificationTokenService verificationTokenMediator;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private MessageSource messageSource;

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
			user = this.userMediator.registerUserAccount(form);
		}

		if (user == null) {
			result.rejectValue("email", "message.regError");
		}

		if (result.hasErrors()) {
			return new ModelAndView(ConstantsViews.USER_REGISTRATION_VIEW, "form", form);
		}

		try {

			String appUrl = request.getContextPath();
			this.applicationEventPublisher
					.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

		} catch (Exception ex) {
			return new ModelAndView(ConstantsViews.USER_REGISTRATION_EMAIL_ERROR_VIEW, "form", form);
		}

		return new ModelAndView(ConstantsViews.USER_REGISTRATION_SUCCESS_VIEW, "form", form);

	}

	@GetMapping(USER_REGISTRATION_CONFIRM_PATH)
	public String registrationConfirm(WebRequest request, Model model, @RequestParam String token) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = this.verificationTokenMediator.findByToken(token);

		if (verificationToken == null) {
			String message = this.messageSource.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return ViewUtils.redirect(ConstantsViews.USER_REGISTRATION_BADUSER_VIEW);
		}

		if (this.verificationTokenMediator.isTokenExpired(verificationToken)) {
			String message = this.messageSource.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", message);
			return ViewUtils.redirect(ConstantsViews.USER_REGISTRATION_BADUSER_VIEW);
		}

		User user = verificationToken.getUser();
		user.setEnabled(true);
		this.userMediator.saveOrUpdate(user);
		return ViewUtils.redirect(ConstantsViews.LOGIN_VIEW);
	}

}
