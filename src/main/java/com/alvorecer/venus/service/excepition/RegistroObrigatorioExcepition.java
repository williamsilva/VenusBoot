package com.alvorecer.venus.service.excepition;

public class RegistroObrigatorioExcepition extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegistroObrigatorioExcepition(String message) {
		super(message);
	}
}