package com.example.sso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.sso.constants.ConstantsViews;
import com.example.sso.dto.UserRegistrationFormDTO;
import com.example.sso.mediator.UserMediator;
import com.example.sso.model.User;

@Controller
public class RegistrationController {

	private static final String USER_REGISTRATION_PATH = "/user/registration";

	@Autowired
	private UserMediator userMediator;

	@GetMapping(USER_REGISTRATION_PATH)
	public String showRegistrationForm(WebRequest request, Model model) {
		model.addAttribute("form", new UserRegistrationFormDTO());
		return ConstantsViews.USER_REGISTRATION_VIEW;
	}

	@PostMapping(USER_REGISTRATION_PATH)
	public ModelAndView registerUserAccount(@ModelAttribute("form") @Valid UserRegistrationFormDTO form,
			BindingResult result, WebRequest request, Errors errors) {
		User user = this.userMediator.findByUserName(form.getFirstName());
		if (errors.hasErrors()) {
			return new ModelAndView(ConstantsViews.USER_REGISTRATION_VIEW, "form", form);
		}

		return null;

	}

}
