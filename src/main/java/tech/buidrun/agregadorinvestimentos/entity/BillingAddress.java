package tech.buidrun.agregadorinvestimentos.entity;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_billingaddress")
public class BillingAddress {
	
	@Id
	@Column(name="account_id") // precisa ser igual ao nome do @JoinColumn
	private UUID id;
	
	// 1:1 um endereço de cobrança está relacionado a uma unica conta
	@OneToOne(cascade = CascadeType.ALL) // um endereço de cobrança é relacionado a uma conta
	@MapsId // indica para o hibernate que ele vai pegar o identificador da entidade Account
	@JoinColumn(name="account_id") // é uma FK de uma outra tabela
	private Account account;
	
	@Column(name="street")
	private String street;
	
	@Column(name="number")
	private Integer number;

	public BillingAddress() {
	
		// TODO Auto-generated constructor stub
	}


	public BillingAddress(UUID id, Account account, String street, Integer number) {
	
		this.id = id;
		this.account = account;
		this.street = street;
		this.number = number;
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	
	
	

}
