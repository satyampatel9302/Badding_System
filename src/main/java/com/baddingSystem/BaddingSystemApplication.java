package com.baddingSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.services.AdminService;

@SpringBootApplication
public class BaddingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaddingSystemApplication.class, args);
	}

		/*@Bean
    CommandLineRunner init(AdminService adminService) {
        return args -> {
            if (adminService.findByUserName("masterc") == null) {
                Admin master = new Admin();
                master.setUsername("master");
                master.setPassword("master123"); // raw password, will be encoded
                master.setEmail("master@admin.com");
                adminService.registerAdmin(master, true); // true → MASTER role
                System.out.println("✅ Master Admin created with username=master and password=master123");
            }
        }; }*/
	
}
