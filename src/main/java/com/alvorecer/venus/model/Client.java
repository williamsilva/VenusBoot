package com.alvorecer.venus.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.alvorecer.venus.model.enun.TypeClientEnun;
import com.alvorecer.venus.model.validation.ClientSequenciProvider;
import com.alvorecer.venus.model.validation.CnpjGroup;
import com.alvorecer.venus.model.validation.CpfGroup;

@Entity
@Table(name = "client")
@GroupSequenceProvider(ClientSequenciProvider.class)
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome do Cliente é obrigatório")
	private String name;

	@NotNull(message = "Tipo pessoa é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "type_client")
	private TypeClientEnun typeClient;

	@NotBlank(message = "CPF/CNPJ é obrigatório")
	@CNPJ(groups = CnpjGroup.class)
	@CPF(groups = CpfGroup.class)
	@Column(name = "cpf_cnpj")
	private String cpfOuCnpj;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "cell_phone")
	private String cellPhone;

	@Email(message = "E-mail informado inválido")
	private String email;

	@Column(name = "date_nascimento")
	private LocalDate dateNascimento;

	private int number;

	private String reference;

	private String comments;

	private String street;

	private String neighborhood;

	@Column(name = "code_postal")
	private String codePostal;

	@NotNull(message = "Informe uma Cidade")
	@ManyToOne()
	@JoinColumn(name = "id_city")
	private City city;

	@OneToMany(mappedBy = "client")
	private List<Attendance> attendance;
	
	@OneToMany(mappedBy = "client")
	private List<Reserve> reserve;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public LocalDate getDateNascimento() {
		return dateNascimento;
	}

	public void setDateNascimento(LocalDate dateNascimento) {
		this.dateNascimento = dateNascimento;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isNew() {
		return this.id == null;
	}

	public boolean isCityNull() {
		return this.city == null;
	}

	public String getCityState() {
		if (this.city != null) {
			return this.city.getName() + "/" + this.city.getState().getInitials();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(this.name);
	}
}
