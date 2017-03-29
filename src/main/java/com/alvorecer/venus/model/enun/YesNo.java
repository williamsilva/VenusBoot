package com.alvorecer.venus.model.enun;

public enum YesNo {

	SIM("Sim"), NAO("Não");

	private String descricao;

	private YesNo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
