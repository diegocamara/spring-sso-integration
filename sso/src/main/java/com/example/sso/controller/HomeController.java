package com.example.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sso.constants.ConstantsViews;

@Controller
public class HomeController {

	private static final String HOME_PATH = "/home";

	@GetMapping(HOME_PATH)
	public String home(Model model) {
		return ConstantsViews.HOME_VIEW;
	}

}
