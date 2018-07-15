package com.example.sso.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.sso.annotation.PasswordMatches;
import com.example.sso.dto.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		UserDTO userDTO = (UserDTO) object;

		if (userDTO.getPassword() == null || userDTO.getMatchingPassword() == null) {
			return false;
		}

		boolean result = userDTO.getPassword().equals(userDTO.getMatchingPassword());

		if (!result) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Passwords don't match").addPropertyNode("matchingPassword")
					.addConstraintViolation();
			return false;
		}

		return true;
	}

}
