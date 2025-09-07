package tech.buidrun.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		//userService.deleteById(userId);
		accountService.associateStock(accountId,aAsDto);
		//return ResponseEntity.noContent().build();
		return ResponseEntity.ok().build();
		
	}

}
