package com.alvorecer.venus.model.enun;

public enum AsPark {

	AMIGO("Amigo"),
	JORNAL("Jornal"),
	PLANFETAGEM("Planfetagem"),
	RADIO("Radio"),
	OUTDOOR("Outdoor"),
	OUTROS("Outros");
    
	private String descricao;

	AsPark(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
