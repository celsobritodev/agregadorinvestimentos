package tech.buidrun.agregadorinvestimentos.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable // indica para o hibernate que vai poder utilizar esta classe como um campo de identificador
            // dentro de nossa entidade
public class AccountStockId {
	// esta classe vai ser uma chave composta entre accountid e stockid
	
	
	@Column(name="account_id")
	private UUID accountId;
	
	@Column(name="stock_id")
	private String stockId;

	public AccountStockId() {
		
		// TODO Auto-generated constructor stub
	}

	public AccountStockId(UUID accountId, String stockId) {
		super();
		this.accountId = accountId;
		this.stockId = stockId;
	}

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	

}
