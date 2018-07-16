package com.example.sso.exception;

public class EmailExistsException extends Throwable {

	private static final long serialVersionUID = 1L;

	public EmailExistsException(String message) {
		super(message);
	}

}
