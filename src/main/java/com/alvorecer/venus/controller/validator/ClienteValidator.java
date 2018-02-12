package com.alvorecer.venus.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alvorecer.venus.model.Client;

@Component
public class ClienteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "city.id", "", "Selecione uma Cidade");

		ValidationUtils.rejectIfEmpty(errors, "city.state.id", "", "Selecione um Estado");

	}
}
