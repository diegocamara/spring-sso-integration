package com.example.sso.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.sso.constants.ConstantsViews;
import com.example.sso.dto.UserDTO;

@Controller
public class RegistrationController {

	private static final String USER_REGISTRATION_PATH = "/user/registration";

	@GetMapping(USER_REGISTRATION_PATH)
	public String showRegistrationForm(WebRequest request, Model model) {
		model.addAttribute("user", new UserDTO());
		return ConstantsViews.USER_REGISTRATION_VIEW;
	}

	@PostMapping(USER_REGISTRATION_PATH)
	public ModelAndView registerUserAccount(@Valid UserDTO user, BindingResult result, WebRequest request,
			Errors errors) {
		return null;
	}

}
