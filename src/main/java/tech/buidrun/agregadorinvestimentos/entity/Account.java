package tech.buidrun.agregadorinvestimentos.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_accounts")
public class Account {
	
	@Id
	@Column(name="account_id")
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID accountId;
	
	// 1:N muitas contas para um usuario
	// varias contas estao relacionadas a um unico usuario
	@ManyToOne 
	// indica que quando criar a tabela "tb_accounts" sera criada uma coluna de
	// "user_id" que será a chave estrangeira da tabela de usuarios
	@JoinColumn(name="user_id") // coluna FK referenciando a tabela de usuarios
	private User user;
	
	
	//1:1 um endereço de cobrança está relacionado a uma unica conta
	@OneToOne(mappedBy="account", cascade = CascadeType.ALL) // indica o campo dentro da entidade BillingAddress
	@PrimaryKeyJoinColumn // passando a primary key da entidade account para a tabela de BillingAddress
	private BillingAddress billingAddress;
	
	
	@Column(name="description")
	private String description;
	
	// 1:N uma conta pode ter varios stocks
	@OneToMany(mappedBy="account")
	private List<AccountStock> accountStocks;


	public Account() {
	
	}


	public Account(UUID accountId, User user, BillingAddress billingAddress, String description,
			List<AccountStock> accountStocks) {

		this.accountId = accountId;
		this.user = user;
		this.billingAddress = billingAddress;
		this.description = description;
		this.accountStocks = accountStocks;
	}


	public UUID getAccountId() {
		return accountId;
	}


	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public BillingAddress getBillingAddress() {
		return billingAddress;
	}


	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}


	public List<AccountStock> getAccountStocks() {
		return accountStocks;
	}


	public void setAccountStocks(List<AccountStock> accountStocks) {
		this.accountStocks = accountStocks;
	}
	
	
	

}
