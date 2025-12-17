package com.baddingSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entityRoles.Role;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	//Search admin by name
	
	Optional<Admin> findByUsername (String Username);
	
	//Give admin by email.
	
	List<Admin> findByEmail (String email);
	
	//Calculate total role count NORMAL/MASTER
	
	long countByRole (Role role);

}
