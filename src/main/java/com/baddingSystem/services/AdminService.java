package com.baddingSystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entityRoles.Role;
import com.baddingSystem.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//Find admin by userName(adminName) 
	
	public Admin findByUserName (String username) {
		return adminRepository.findByUsername(username).orElse(null);
	}
	
	//Find admin by id
	
	public Admin findById (Long id) {
		return adminRepository.findById(id).orElse(null);
	}
	
	//Find all admins
	
	public List<Admin> getAllAdmins(){
		return adminRepository.findAll();
	}
	
	//Remove admin 
	
	public void removeAdmin(Long id) {
		adminRepository.deleteById(id);
	}
	
	//Calculate admin according to Role
	
	public long countByRole (Role role) {
		return adminRepository.countByRole(role);
	}
	
	//Update role like MASTER -> NORMAL , NORMAL -> MASTER
	
	public void updateRole(Long id, Role role) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() ->new  RuntimeException ("Admin are not found !!"));
		admin.setRole(role);
		adminRepository.save(admin);
	}
	
	// Register / Save admin 
	
	public Admin registerAdmin (Admin admin, boolean master ) {
		admin.setRole(master ? Role.MASTER : Role.NORMAL);
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return adminRepository.save(admin);
	}
}
