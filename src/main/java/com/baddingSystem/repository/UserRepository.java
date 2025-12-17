package com.baddingSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baddingSystem.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByName(String name);
	
	List<User> findAllByEmail(String email);
	
	List<User> findAll();

}
