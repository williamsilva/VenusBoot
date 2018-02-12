package com.alvorecer.venus.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alvorecer.venus.model.Reserve;

@Component
public class ReserveValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Reserve.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "client.id", "","Selecione um Cliente");
		
		Reserve reserve = (Reserve) target;		
		validarInformouItens(errors, reserve);
		
		validarValorTotalNegativo(errors, reserve);
	}

	private void validarValorTotalNegativo(Errors errors, Reserve reserve) {
		if(reserve.getValor().compareTo(BigDecimal.ZERO) < 0){
			errors.reject("", "Valor Total não pode ser Negativo");
		}
	}

	private void validarInformouItens(Errors errors, Reserve reserve) {
		if(reserve.getItens().isEmpty()){
			errors.reject("","Adicione pelomenos um produto para o voucher");
		}
	}

}
