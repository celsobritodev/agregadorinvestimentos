package tech.buidrun.agregadorinvestimentos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tech.buidrun.agregadorinvestimentos.client.BrapiClient;
import tech.buidrun.agregadorinvestimentos.controller.dto.AccountStockResponseDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.AssociateAccountStockDto;
import tech.buidrun.agregadorinvestimentos.entity.AccountStock;
import tech.buidrun.agregadorinvestimentos.entity.AccountStockId;
import tech.buidrun.agregadorinvestimentos.repository.AccountRepository;
import tech.buidrun.agregadorinvestimentos.repository.AccountStockRepository;
import tech.buidrun.agregadorinvestimentos.repository.StockRepository;

@Service
public class AccountService {
	
	@Value("#{environment.TOKEN}") // vai referenciar a variavel de ambiente
	private String TOKEN;
	
	private AccountRepository accountRepository;
	
	private StockRepository stockRepository;
	
	private AccountStockRepository accountStockRepository;
	
	private BrapiClient brapiClient;

	

	public AccountService(AccountRepository accountRepository,
			StockRepository stockRepository,
			AccountStockRepository accountStockRepository,
			BrapiClient brapiClient) {

		this.accountRepository = accountRepository;
		this.stockRepository = stockRepository;
		this.accountStockRepository = accountStockRepository;
		this.brapiClient = brapiClient;
	}




	public void associateStock(String accountId, AssociateAccountStockDto aAsDto) {
		
		
		//  A CONTA EXISTE?
		var account = accountRepository.findById(UUID.fromString(accountId))
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		// A ACAO EXISTE?
		var stock = stockRepository.findById(aAsDto.stockId())
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		
		// DTO -> ENTITY
		var id = new AccountStockId(account.getAccountId(),stock.getStockId());
		var entity = new AccountStock(
				id,
				account,
				stock,
				aAsDto.quantity()
				);
		
		accountStockRepository.save(entity);
				
		
		
	}



	public List<AccountStockResponseDto> listStocks(String accountId) {
	
		
		// A CONTA EXISTE?
		var account = accountRepository.findById(UUID.fromString(accountId))
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return account.getAccountStocks()
		              .stream()
		              .map(as ->
                         new AccountStockResponseDto(
                        		 as.getStock().getStockId(),
                        		 as.getQuantity(),
                        		 getTotal(as.getQuantity(),as.getStock().getStockId())
                        		 ))
		              .toList();
		
	
	}



	private double getTotal(Integer quantity, String stockId) {

		var response = brapiClient.getQuote(TOKEN, stockId);
		
		var price = response.results().getFirst().regularMarketPrice();
				
		
		return quantity*price;
	}
	
	

}
