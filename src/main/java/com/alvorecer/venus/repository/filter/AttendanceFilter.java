package com.alvorecer.venus.repository.filter;

import java.time.LocalDate;

import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.model.enun.AsPark;
import com.alvorecer.venus.model.enun.Channel;
import com.alvorecer.venus.model.enun.Subject;
import com.alvorecer.venus.model.enun.TypeClientEnun;
import com.alvorecer.venus.model.enun.YesNo;

public class AttendanceFilter {

	private LocalDate dateRegisterInicial;
	private LocalDate dateRegisterFinal;
	private Channel channel;
	private YesNo knowsPark;
	private AsPark asPark;
	private Subject subject;
	private YesNo closed;
	private YesNo returnContact;
	private LocalDate dateReturn;
	private String comments;
	private Client client;
	private TypeClientEnun typeClient;
	private String protocol;
	private String nomeCliente;
	private String cpfOuCnpj;
	private String email;

	public LocalDate getDateRegisterInicial() {
		return dateRegisterInicial;
	}

	public void setDateRegisterInicial(LocalDate dateRegisterInicial) {
		this.dateRegisterInicial = dateRegisterInicial;
	}

	public LocalDate getDateRegisterFinal() {
		return dateRegisterFinal;
	}

	public void setDateRegisterFinal(LocalDate dateRegisterFinal) {
		this.dateRegisterFinal = dateRegisterFinal;
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

	public TypeClientEnun getTypeClient() {
		return typeClient;
	}

	public void setTypeClient(TypeClientEnun typeClient) {
		this.typeClient = typeClient;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
