package com.gbtech.emails.rest.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gbtech.emails.rest.controller.bean.Email;

public class EmailValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return Email.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {

	}

}
