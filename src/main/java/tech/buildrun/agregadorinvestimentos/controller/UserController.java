package tech.buildrun.agregadorinvestimentos.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.buildrun.agregadorinvestimentos.entity.User;
import tech.buildrun.agregadorinvestimentos.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	private UserService userService;
	
	
	public UserController(UserService userService) {
	
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
		var userId = userService.createUser(createUserDto);
		return ResponseEntity.created(URI.create("/v1/users/"+userId.toString())).build();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
		var user = userService.getUserById(userId);
		
		/*
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else
			return ResponseEntity.notFound().build();
		*/
		
		// o codigo acima pode ser substituido pelo codigo  abaixo
		return user.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
		
	}
	
	
	@GetMapping
	public ResponseEntity<List<User>> listUsers() {
		var users = userService.listUsers();
		return ResponseEntity.ok(users);
		
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
			                                   @RequestBody UpdateUserDto updateUserDto) {
		userService.updateUserById(userId, updateUserDto);
		return ResponseEntity.noContent().build();
	}
	
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
	     userService.deleteById(userId);
	     return ResponseEntity.noContent().build();
	
	}
	

}
