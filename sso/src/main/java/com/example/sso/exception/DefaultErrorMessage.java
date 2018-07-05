package com.example.sso.exception;

import java.util.LinkedList;
import java.util.List;

public class DefaultErrorMessage {

	private String code;
	private String status;
	private List<String> errors = new LinkedList<String>();

	public DefaultErrorMessage(String code, String status, String error) {
		this.code = code;
		this.status = status;
		this.errors.add(error);
	}

	public DefaultErrorMessage(String code, String status, List<String> errors) {
		this.code = code;
		this.status = status;
		this.errors = errors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
