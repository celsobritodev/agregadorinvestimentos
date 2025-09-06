package tech.buidrun.agregadorinvestimentos.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tech.buidrun.agregadorinvestimentos.controller.dto.AccountResponseDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.CreateAccountDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.CreateUserDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.UpdateUserDto;
import tech.buidrun.agregadorinvestimentos.entity.Account;
import tech.buidrun.agregadorinvestimentos.entity.BillingAddress;
import tech.buidrun.agregadorinvestimentos.entity.User;
import tech.buidrun.agregadorinvestimentos.repository.AccountRepository;
import tech.buidrun.agregadorinvestimentos.repository.BillingAddressRepository;
import tech.buidrun.agregadorinvestimentos.repository.UserRepository;

@Service
@Transactional // DEEPSEEK
public class UserService {

	// injetando
	@Autowired // DEEPSEEK
	private UserRepository userRepository;
	
	// injetando
	@Autowired // DEEPSEEK
	private AccountRepository accountRepository;
	
	// injetando
	@Autowired // DEEPSEEK
	private BillingAddressRepository billingAddressRepository;

	
	public UserService(UserRepository userRepository, AccountRepository accountRepository,
			BillingAddressRepository billingAddressRepository) {
		super();
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.billingAddressRepository = billingAddressRepository;
	}

	public UUID createUser(CreateUserDto createUserDto) {

		// DTO -> ENTITY
		// idUser se não for null dá erro ao salvar
		// UUID idUser = UUID.randomUUID();
		UUID idUser = null;
		String userName = createUserDto.username();
		String email = createUserDto.email();
		// String email = null;

		String password = createUserDto.password();

		var entity = new User(idUser, userName, email, password, Instant.now(), null);

		var userSaved = userRepository.save(entity);
		return userSaved.getUserId();

	}

	public Optional<User> getUserById(String userId) {

		return userRepository.findById(UUID.fromString(userId));

	}

	public List<User> listUsers() {
		return userRepository.findAll();
	}

	public void deleteById(String userId) {

		var id = UUID.fromString(userId);

		var userExists = userRepository.existsById(id);

		if (userExists) {
			userRepository.deleteById(id);
		}

	}

	public void createAccount(String userId, CreateAccountDto createAccountDto) {
		// verificano se usuario existe ante de fazer a relação
		var user = userRepository.findById(UUID.fromString(userId)) // se usuario nao existe gera excessao
		              .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));// findById retorna Optional<>
		
		// convertendo DTO -> ENTITY  / CRIA A CONTA PRIMEIRO
		var account = new Account(
				null, // antes era UUID.randomUUID que gera uma excessão
				user,
				null,
				createAccountDto.description(),
				new ArrayList<>()
				);
		
		var accountCreated = accountRepository.save(account);
		
		var billingAddress = new BillingAddress (
				//accountCreated.getAccountId(),
				null, // DEEPSEEK
				//account,
				accountCreated, // DEEPSEEK
				createAccountDto.street(),
				createAccountDto.number()
				);
		

		
		// Salvar o billingAddress
		billingAddressRepository.save(billingAddress);
				
		
	}

	public void updateUserById(String userId, UpdateUserDto updateUserDto) {

		var id = UUID.fromString(userId);

		var userEntity = userRepository.findById(id);

		if (userEntity.isPresent()) {
			var user = userEntity.get();

			if (updateUserDto.username() != null) {
				user.setUsername(updateUserDto.username());
			}

			if (updateUserDto.password() != null) {
				user.setPassword(updateUserDto.password());
			}

			userRepository.save(user);

		}

	}

	public List<AccountResponseDto> listAccounts(String userId) {
		// verificano se usuario existe ante de fazer a relação
		var user = userRepository.findById(UUID.fromString(userId)) // se usuario nao existe gera excessao
				              .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));// findById retorna Optional<>
		return user.getAccounts()
		 .stream()
		 .map(ac-> new AccountResponseDto(ac.getAccountId().toString(),ac.getDescription()))
		 .toList();
	}

}
