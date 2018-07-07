package com.example.sso.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class NameFormatter implements Formatter<String> {

	@Override
	public String print(String input, Locale locale) {
		return formatName(input, locale);
	}

	@Override
	public String parse(String input, Locale locale) throws ParseException {
		return formatName(input, locale);
	}

	private String formatName(String input, Locale locale) {
		return StringUtils.replace(input, " ", ",");
	}
}