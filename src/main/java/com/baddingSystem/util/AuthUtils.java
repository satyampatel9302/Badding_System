package com.baddingSystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.repository.AdminRepository;


@Component
public class AuthUtils {
    @Autowired
    private AdminRepository adminRepository;

    public Admin getCurrentAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return adminRepository.findByUsername(username).orElseThrow();
    }
  }
