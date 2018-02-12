package com.alvorecer.venus.model.enun;

public enum StatusVouchers {
	
	ORCAMENTO("Orçamento"),
	TROCADO("Trocado"),
	VENCIDO("Vencido"),
	CANCELADO("Cancelado");
    
	private String descricao;

	StatusVouchers(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
