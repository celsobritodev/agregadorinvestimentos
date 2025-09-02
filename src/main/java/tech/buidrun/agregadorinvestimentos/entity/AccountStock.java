package tech.buidrun.agregadorinvestimentos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_accounts_stocks")
public class AccountStock {
	
	@EmbeddedId
	private AccountStockId id;
	
	@ManyToOne // varias AccountStock vao estar relacionadas com uma unica conta
	@MapsId("accountId")
	@JoinColumn(name="account_id") // nome da coluna que vai ser o FK
	private Account account;
	
	@ManyToOne // varias AccountStock vao estar relacionadas com uma unica stock
	@MapsId("stockId")
	@JoinColumn(name="stock_id") // nome da coluna que vai ser o FK
	private Stock stock;
	
	@Column(name = "quantity")
	private Integer quantity;

	public AccountStock() {

		// TODO Auto-generated constructor stub
	}

	public AccountStock(AccountStockId id, Account account, Stock stock, Integer quantity) {
		super();
		this.id = id;
		this.account = account;
		this.stock = stock;
		this.quantity = quantity;
	}

	public AccountStockId getId() {
		return id;
	}

	public void setId(AccountStockId id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	

}
