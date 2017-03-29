package com.alvorecer.venus.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.alvorecer.venus.model.enun.AsPark;
import com.alvorecer.venus.model.enun.Channel;
import com.alvorecer.venus.model.enun.Subject;
import com.alvorecer.venus.model.enun.TypeClient;
import com.alvorecer.venus.model.enun.YesNo;

@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "hour_register")
	private LocalDateTime hourRegister;

	@Column(name = "date_register")
	private LocalDate dateRegister;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Informe o canal de contato")
	@Column(name = "channel")
	private Channel channel;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Informe se conhece o parque")
	@Column(name = "knws_park")
	private YesNo knowsPark;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Informe como soube do parque.")
	@Column(name = "as_park")
	private AsPark asPark;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Informe o motivo do contato.")
	@Column(name = "subject")
	private Subject subject;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Informe o Status")
	@Column(name = "closed")
	private YesNo closed;

	@Enumerated(EnumType.STRING)
	@Column(name = "return_contact")
	private YesNo returnContact;

	@Column(name = "date_return")
	private LocalDate dateReturn;

	@Column(name = "comments")
	private String comments;

	@ManyToOne
	@NotNull(message = "Informe um Cliente")
	@JoinColumn(name = "id_client")
	private Client client;

	@Transient
	@NotNull(message = "Informe o tipo do cliente")
	@Enumerated(EnumType.STRING)
	private TypeClient typeClient;

	@Transient
	private String cpfOuCnpj;

	public Attendance() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getHourRegister() {
		return hourRegister;
	}

	public void setHourRegister(LocalDateTime hourRegister) {
		this.hourRegister = hourRegister;
	}

	public LocalDate getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(LocalDate dateRegister) {
		this.dateRegister = dateRegister;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public YesNo getKnowsPark() {
		return knowsPark;
	}

	public void setKnowsPark(YesNo knowsPark) {
		this.knowsPark = knowsPark;
	}

	public AsPark getAsPark() {
		return asPark;
	}

	public void setAsPark(AsPark asPark) {
		this.asPark = asPark;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public YesNo getClosed() {
		return closed;
	}

	public void setClosed(YesNo closed) {
		this.closed = closed;
	}

	public YesNo getReturnContact() {
		return returnContact;
	}

	public void setReturnContact(YesNo returnContact) {
		this.returnContact = returnContact;
	}

	public LocalDate getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(LocalDate dateReturn) {
		this.dateReturn = dateReturn;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public TypeClient getTypeClient() {
		return typeClient;
	}

	public void setTypeClient(TypeClient typeClient) {
		this.typeClient = typeClient;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
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
		Attendance other = (Attendance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	// 23:15
}
