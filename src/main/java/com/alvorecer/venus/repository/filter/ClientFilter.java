package com.alvorecer.venus.repository.filter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.alvorecer.venus.model.enun.TypeClientEnun;

public class ClientFilter {

	private String name;
	private String email;

	@Enumerated(EnumType.STRING)
	private TypeClientEnun typeClient;

	private String cpfOuCnpj;

	private Long city;

	private Long state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TypeClientEnun getTypeClient() {
		return typeClient;
	}

	public void setTypeClient(TypeClientEnun typeClient) {
		this.typeClient = typeClient;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}
