package com.alvorecer.venus.model.enun;

public enum Channel {

	CHAT("Chat"), 
	EMAIL("E-mail"),
	TELEFONE("Telefone"),
	OUTROS("Outros");

	private String descricao;

	private Channel(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
