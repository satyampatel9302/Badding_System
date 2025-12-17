package com.baddingSystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baddingSystem.entities.User;
import com.baddingSystem.entityRoles.Role;
import com.baddingSystem.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Save user in database
	
	public User registerUser(User user) {
		user.setEmail(user.getEmail());
		user.setName(user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		return userRepository.save(user);
	}
	
	//Get user by User Email
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
	
	//Remove User
	
	public void removeUser(Long id) {
		userRepository.deleteById(id);
	}
	
	//Get all User 
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
}
