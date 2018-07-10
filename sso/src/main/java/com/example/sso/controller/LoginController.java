package com.example.sso.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sso.constants.ConstantsViews;

@Controller
public class LoginController {

	private static final String LOGIN_PATH = "/login";

	@GetMapping(LOGIN_PATH)
	public String login(Model model) {
		model.addAttribute("serverTime", LocalDateTime.now());

		return ConstantsViews.LOGIN_VIEW;
	}

}
