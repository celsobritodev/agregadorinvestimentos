package tech.buidrun.agregadorinvestimentos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tech.buidrun.agregadorinvestimentos.controller.dto.CreateUserDto;
import tech.buidrun.agregadorinvestimentos.controller.dto.UpdateUserDto;
import tech.buidrun.agregadorinvestimentos.entity.User;
import tech.buidrun.agregadorinvestimentos.repository.UserRepository;

@ExtendWith(MockitoExtension.class) // permite o junit utilizar o mockito
public class UserServiceTest {

	// 3- PASSOS PARA UM TESTE
	// 1- Arrange - Arrumar organizar tudo o que precisa
	// 2- Act - chamar o trecho de codigo que se deseja testar
	// 3- Assert - todas verificações - se o codigo executou o que tinha que
	// executar

	@Mock // vai "mockar" a classe UserRepository
	private UserRepository userRepository;

	// indicando ao mockito para criar uma instancia de UserService
	// injetanto os mocks; no caso o UserRepository
	@InjectMocks // vai injetar o mock na classe UserService
	private UserService userService; // <== classe que o mockito vai criar
	
	@Captor
	private ArgumentCaptor<User> userArgumentCaptor;
	
	@Captor
	private ArgumentCaptor<UUID> uuidArgumentCaptor;
	

	// definindo uma subclasse para organizar os testes
	@Nested // <= anotacao pra indicar que é uma subclasse
	class createUser {

		// deveria criar um usuario com sucesso
		@Test
		@DisplayName("Should create a user with sucess")
		void shouldCreateAUserWithSucess() {

			// 3- PASSOS PARA UM TESTE

			// 1- Arrange - Arrumar organizar tudo o que precisa
			var user = new User(
					UUID.randomUUID(), 
					"username", 
					"email@email.com", 
					"password",
					Instant.now(),
					null);
			// quanto userRepository chamar o metodo save, deve retornar um 'user'
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
			var input = new CreateUserDto(
					"username",
					"email@email.com",
					"password");

			// 2- Act - chamar o trecho de codigo que se deseja testar
			var output = userService.createUser(input);

			// 3- Assert - todas verificações - se o codigo executou o que tinha que
			// executar
			assertNotNull(output);
			
			var userCaptured = userArgumentCaptor.getValue();
			assertEquals(input.username(), userCaptured.getUsername());
			assertEquals(input.email(), userCaptured.getEmail());
			assertEquals(input.password(), userCaptured.getPassword());
			

		}

		// deveria lancar uma excessao se ocorrer um erro
		@Test
		@DisplayName("Should throw exception when error occurs")
		void shouldThrowExceptionWhenErrorOccurs() {

			// vai lançar uma exception ao chamar userRepository.save()
			// 1- Arrange - Arrumar organizar tudo o que precisa
			doThrow(new RuntimeException()).when(userRepository).save(any());
			var input = new CreateUserDto(
					"username",
					"email@email.com",
					"password");

			// 2- Act && Assert - chamar o trecho de codigo que se deseja testar
			assertThrows(RuntimeException.class,()-> userService.createUser(input));

			// 3- Assert - todas verificações - se o codigo executou o que tinha que
		

		}

	}
	
	
	@Nested
	class getUserById {
		
		@Test
		@DisplayName("Should get user by id with sucess when optional is present")
		void shouldGetUserByIdWithSucessWhenOptionalIsPresent() {
			
			// Arrange
			var user = new User(
					UUID.randomUUID(), 
					"username", 
					"email@email.com", 
					"password",
					Instant.now(),
					null);
			// quanto userRepository chamar o metodo findById, deve retornar um 'Optional de user'
			doReturn(Optional.of(user))
			      .when(userRepository)
			      .findById(uuidArgumentCaptor.capture());
			
			// Act
			var output = userService.getUserById(user.getUserId().toString());
			
			// Assert
			assertTrue(output.isPresent());
			assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());
			
			
		}
		
		@Test
		@DisplayName("Should get user by id with sucess when optional is empty")
		void shouldGetUserByIdWithSucessWhenOptionalIsEmpty() {
			
			// Arrange
			var userId = UUID.randomUUID();
			// quanto userRepository chamar o metodo findById, deve retornar um 'Optional de user'
			doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());
			
			// Act
			var output = userService.getUserById(userId.toString());
			
			// Assert
			assertTrue(output.isEmpty()); // testando se a saida esta mesmo vazia
			var userIdSender = userId; // ID QUE ESTOU MANDANDO
			var userIdReceiver = uuidArgumentCaptor.getValue(); // ID QUE CHEGOU NO findById
			assertEquals(userIdSender,userIdReceiver); // verifica se o argumento
			
			
		}
		
		
	}
	
	@Nested
	class ListUsers {
		
		@Test
		@DisplayName("Should return all users with sucess")
		void shouldReturnAllUsersWithSuccess() {
			
			// Arrange
			var user = new User(
					UUID.randomUUID(), 
					"username", 
					"email@email.com", 
					"password",
					Instant.now(),
					null);
			var userList = List.of(user);
			// quanto userRepository chamar o metodo findAll, deve retornar um List de user'
			doReturn(userList)
			      .when(userRepository)
			      .findAll();
			
			// Act
			var output = userService.listUsers();
			
			// Assert
			assertNotNull(output);
			assertEquals(userList.size(),output.size());
			
			
			
			
		}
	}
	
	
	@Nested
	class deleteById {
		
		
		@Test
		@DisplayName("Should delete user with success when user existts")
		void shouldDeleteUserWithSucessWhenUserExists() {
			
			           // Arrange
			           // quanto userRepository chamar o metodo existsById, deve retornar true
						doReturn(true)
						.when(userRepository)
						.existsById(uuidArgumentCaptor.capture());
						
						// quanto userRepository chamar o metodo deleteById, deve retornar void
						doNothing()
						 .when(userRepository)
						 .deleteById(uuidArgumentCaptor.capture());
						
						// Act
						var userId = UUID.randomUUID();
						userService.deleteById(userId.toString());
						
						// Assert
						var idList = uuidArgumentCaptor.getAllValues();
						assertEquals(userId,idList.get(0));
						assertEquals(userId,idList.get(1));
						
						verify(userRepository,times(1)).existsById(idList.get(0));
						verify(userRepository,times(1)).deleteById(idList.get(0));
			
		}
		
		
		@Test
		@DisplayName("Should not delete user with success when user NOT existts")
		void shouldNotDeleteUserWhenUserNotExists() {
			
           // Arrange
           // quanto userRepository chamar o metodo existsById, deve retornar true
			doReturn(false)
			.when(userRepository)
			.existsById(uuidArgumentCaptor.capture());
			
			// Act
			var userId = UUID.randomUUID();
			userService.deleteById(userId.toString());
			
			// Assert
			assertEquals(userId,uuidArgumentCaptor.getValue());
			
			verify(userRepository,times(1)).existsById(uuidArgumentCaptor.getValue());
			verify(userRepository,times(0)).deleteById(any());
		
			
		}
		
	}
	
	@Nested
	class updateUserById {
		
		
		@Test
		@DisplayName("Should update user by id when user exists and username and password is filled")
		void shouldUpdateUserByWhenUserExistsAndUsernameAndPasswordIsFilled() {
			
			// Arrange
			var updateUserDto = new UpdateUserDto(
					"newUsername",
					"newPassword");
			
			var user = new User(
					UUID.randomUUID(), 
					"username", 
					"email@email.com", 
					"password",
					Instant.now(),
					null);
			
			// quanto userRepository chamar o metodo findById, deve retornar um 'Optional de user'
			doReturn(Optional.of(user))
			      .when(userRepository)
			      .findById(uuidArgumentCaptor.capture());
			doReturn(user)
		      .when(userRepository)
		      .save(userArgumentCaptor.capture());
			
			// Act
			 userService.updateUserById(user.getUserId().toString(),updateUserDto);
			
			// Assert
			 // passou o identificador certo para o repository?
		     assertEquals(user.getUserId(),uuidArgumentCaptor.getValue());
		     
		     // passou o usuario corretamente para o repository e que ele atualizou o campo?
		     var userCaptured = userArgumentCaptor.getValue();
		     
		     // o username que eu mandei para atualizar é igual ao username que retornou? (que foi capturado)
		     assertEquals(updateUserDto.username(),userCaptured.getUsername());
		     // a passord que eu mandei para atualizr é igual a password que retornou? ( que foi capturada)
		     assertEquals(updateUserDto.password(),userCaptured.getPassword());
		     
		     // o userRepository deve ser chamado 1 vez no metodo findById como o uuidArgumentCaptor
		     verify(userRepository,times(1)).findById(uuidArgumentCaptor.getValue());
		     
		     // o userRepository deve ser chamado 1 vez no metodo save com o user
		     verify(userRepository, times(1)).save(user);
		     
			
			
		}
		
		
		
		@Test
		@DisplayName("Should not update user when user not exists")
		void shouldNotUpdateUserWhenUserNotExists() {
			
			// Arrange
			var updateUserDto = new UpdateUserDto(
					"newUsername",
					"newPassword");
			
			// quando userRepository chamar o metodo findById, deve retornar um 'Optional de empty'
			var userId = UUID.randomUUID();
			doReturn(Optional.empty())
			      .when(userRepository)
			      .findById(uuidArgumentCaptor.capture());
			
			// Act
			 userService.updateUserById(userId.toString(),updateUserDto);
			
			// Assert
			 // passou o identificador certo para o repository?
		     assertEquals(userId,uuidArgumentCaptor.getValue());
		     
		     // o userRepository deve ser chamado 1 vez no metodo findById como o uuidArgumentCaptor
		     verify(userRepository,times(1)).findById(uuidArgumentCaptor.getValue());
		     
		     // o userRepository nao deve ser chamado nenhuma vez
		     verify(userRepository, times(0)).save(any());
		     
			
			
		}		
		
	}
	

}
