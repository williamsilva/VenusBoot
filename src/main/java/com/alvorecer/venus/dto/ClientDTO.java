package com.alvorecer.venus.dto;

public class ClientDTO {

	private String name;
	private String cpfOuCnpj;
	private String phoneNumber;
	private String cellPhone;
	private String email;

	public ClientDTO(String name, String cpfOuCnpj, String phoneNumber, String cellPhone, String email) {
		super();
		this.name = name;
		this.cpfOuCnpj = cpfOuCnpj;
		this.phoneNumber = phoneNumber;
		this.cellPhone = cellPhone;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
