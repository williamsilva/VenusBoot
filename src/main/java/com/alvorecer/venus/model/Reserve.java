package com.alvorecer.venus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.alvorecer.venus.model.enun.StatusVouchers;

@Entity
@Table(name = "reserve")
@DynamicUpdate
public class Reserve implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "visit_date")
	private LocalDate visitDate;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.50", message = "O valor deve ser maior que R$0,50")
	@DecimalMax(value = "9999999.99", message = "O valor deve ser menor que R$9.999.999,99")
	private BigDecimal valor = BigDecimal.ZERO;

	@Column(name = "valor_antecipado")
	private BigDecimal valorAntecipado;

	private String comments;

	private String voucher;

	@NotNull(message = "O Status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "status_voucher")
	private StatusVouchers statusVouchers = StatusVouchers.ORCAMENTO;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private Use use;

	@ManyToOne
	@NotNull(message = "Informe um Cliente")
	@JoinColumn(name = "id_client")
	private Client client;

	@OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVoucher> itens = new ArrayList<>();

	@Transient
	private String uuid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public StatusVouchers getStatusVouchers() {
		return statusVouchers;
	}

	public void setStatusVouchers(StatusVouchers statusVouchers) {
		this.statusVouchers = statusVouchers;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BigDecimal getValorAntecipado() {
		return valorAntecipado;
	}

	public List<ItemVoucher> getItens() {
		return itens;
	}

	public void setItens(List<ItemVoucher> itens) {
		this.itens = itens;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Use getUse() {
		return use;
	}

	public void setUse(Use use) {
		this.use = use;
	}

	public boolean isNew() {
		return this.id == null;
	}

	public BigDecimal getCalcularValorTotalItens() {
		return getItens().stream().map(ItemVoucher::getValueTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public void setValorAntecipado(BigDecimal valorAntecipado) {
		this.valorAntecipado = valorAntecipado;
	}

	public void addItens(List<ItemVoucher> itens) {
		this.itens = itens;
		this.itens.forEach(i -> i.setReserve(this));

	}

	public Long getDiasCriacao() {
		LocalDate inicio = creationDate != null ? creationDate.toLocalDate() : LocalDate.now();
		return ChronoUnit.DAYS.between(inicio, LocalDate.now());
	}

	public void calcularValorTotal() {
		this.valor = calcularValorTotal(getCalcularValorTotalItens(), getValorAntecipado());
	}

	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorAntecipado) {
		BigDecimal valorTotal = valorTotalItens.subtract(Optional.ofNullable(valorAntecipado).orElse(BigDecimal.ZERO));
		return valorTotal;
	}

	public boolean isSalvarPermitido() {
		return !statusVouchers.equals(StatusVouchers.CANCELADO);
	}
	
	public boolean isSalvarProibido(){
		return !isSalvarPermitido();
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
		Reserve other = (Reserve) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
