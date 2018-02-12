package com.alvorecer.venus.model.enun;

public enum Status {
	
	ATIVO("Ativo"),
	CANCELADO("Cancelado"),
	BLOQUEADO("Bloqueado");
    
	private String descricao;

	Status(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
