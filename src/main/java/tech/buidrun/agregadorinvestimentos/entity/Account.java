package tech.buidrun.agregadorinvestimentos.entity;

import java.util.List;
import java.util.UUID;

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
	
	@ManyToOne // varias contas tem um unico usuario
	// indica que quando criar a tabela "tb_accounts" sera criada uma coluna de
	// "user_id" que será a chave estrangeira da tabela de usuarios
	@JoinColumn(name="user_id") // coluna FK referenciando a tabela de usuarios
	private User user;
	
	@OneToOne(mappedBy="account") // indica o campo dentro da enttidade BillingAddress
	@PrimaryKeyJoinColumn // passando a primary key da entridde account para a tabela de BillingAddress
	private BillingAddress billingAddress;
	
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy="account")
	private List<AccountStock> accountStocks;


	public Account() {
	
		// TODO Auto-generated constructor stub
	}


	public Account(UUID accountId, String description) {
	
		this.accountId = accountId;
		this.description = description;
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
	
	
	

}
