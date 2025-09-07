package tech.buidrun.agregadorinvestimentos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.buidrun.agregadorinvestimentos.controller.dto.AccountStockResponseDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.AssociateAccountStockDto;
import tech.buidrun.agregadorinvestimentos.service.AccountService;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
	
	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/{accountId}/stocks")
	public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
			@RequestBody AssociateAccountStockDto aAsDto) {
	
		accountService.associateStock(accountId,aAsDto);
	
		return ResponseEntity.ok().build();
		
	}
	
	
	@GetMapping("/{accountId}/stocks")
	public ResponseEntity<List<AccountStockResponseDto>> associateStock(@PathVariable("accountId") String accountId) {

		var stocks = accountService.listStocks(accountId);
	
		return ResponseEntity.ok(stocks);
		
	}


}
