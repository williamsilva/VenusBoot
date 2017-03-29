package com.alvorecer.venus.model.enun;

public enum Subject {
	
	BOLETO("Boleto"),
	PORTAL("Portal"),
	TITULO("Venda Título"),
	EXCURSOES("Excuçoes"),
	HOTEL("Hotel"),
	OUTROS("Outros");
	
	private String descricao;
	
	private Subject(String descriao) {
		this.descricao = descriao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}