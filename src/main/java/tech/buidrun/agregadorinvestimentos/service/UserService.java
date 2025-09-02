package tech.buidrun.agregadorinvestimentos.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import tech.buidrun.agregadorinvestimentos.controller.dto.CreateUserDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.UpdateUserDto;
import tech.buidrun.agregadorinvestimentos.entity.User;
import tech.buidrun.agregadorinvestimentos.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UUID createUser(CreateUserDto createUserDto) {
    
		// DTO -> ENTITY
		// idUser se não for null dá erro ao salvar
		// UUID idUser = UUID.randomUUID();
		UUID idUser = null;
		String userName = createUserDto.username();
		String email = createUserDto.email();
	//	String email = null;
		
			
		String password = createUserDto.password();

		var entity = new User(idUser,
				              userName,
				              email,
				              password,
				              Instant.now(),
				              null);

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

}
