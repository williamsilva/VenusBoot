package com.alvorecer.venus.service.excepition;

public class RegistroObrigatorioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RegistroObrigatorioException(String message) {
		super(message);
	}

}
