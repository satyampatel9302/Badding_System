package com.baddingSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.repository.AdminRepository;

@Service
public class CustomAdminDetailsService implements UserDetailsService{
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException ("Admin not found !!"));
		return User.builder()
				.username(admin.getUsername())
				.password(admin.getPassword())
				.roles(admin.getRole().name())
				.build();
				
	}

}
