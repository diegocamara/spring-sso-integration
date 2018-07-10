package com.example.sso.util;

public class ViewUtils {

	private static final String REDIRECT_PREFFIX = "redirect:";

	public static String redirect(String viewName) {
		return REDIRECT_PREFFIX.concat(viewName);
	}

}
