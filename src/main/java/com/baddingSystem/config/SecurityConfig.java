package com.baddingSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.baddingSystem.services.CustomAdminDetailsService;
import com.baddingSystem.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private CustomAdminDetailsService adminDetailsService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain adminSecurity(HttpSecurity http) throws Exception {
		http
		.securityMatcher("/admin/**")
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/admin/login",
						"/admin/register",
						"/admin/home"
						).permitAll()
				.requestMatchers("/admin/master/**").hasRole("MASTER")
				.requestMatchers("/admin/**").hasAnyRole("NORMAL","MASTER")
				.anyRequest().authenticated()
				)
		.formLogin(form -> form
		.loginPage("/admin/login")
		.loginProcessingUrl("/admin/login")
		.defaultSuccessUrl("/admin/dashboard", true)
		.failureUrl("/admin/login?error=true")
				)
		.exceptionHandling(ex -> ex
				.authenticationEntryPoint(new CustomAuthenticationAdminEntryPoint())
				.accessDeniedPage("/admin/login")
				)
		.logout(logout -> logout
				.logoutUrl("/admin/logout")
				.logoutSuccessUrl("/admin/login?logout")
				)
		.userDetailsService(adminDetailsService)
		.csrf(csrf -> csrf.disable());
		return http.build();
		
	}
	
	
	@Bean
	public SecurityFilterChain userSecurity(HttpSecurity http) throws Exception {
		
		http
		 .securityMatcher("/user/**")
		 .authorizeHttpRequests(auth -> auth
				 .requestMatchers(
						 "/user/home",
						 "/user/login",
						 "/user/register",
						 "/user/shop",
						 "/user/category/**"
						 ).permitAll()
				 .requestMatchers("/user/bid").hasRole("USER")
				 .anyRequest().hasRole("USER")
				 )
		 .formLogin(form -> form
				 .loginPage("/user/login")
				 .loginProcessingUrl("/user/login")
				 .defaultSuccessUrl("/user/home", true)
				 .failureUrl("/user/login?error=true")
				 )
		 .exceptionHandling(ex -> ex
                 .authenticationEntryPoint(new CustomAuthenticationUserEntryPoint()) // handle unauthenticated users
                 .accessDeniedPage("/user/login") // for logged in but unauthorized users
             )
		 .logout(logout -> logout
				 .logoutUrl("/user/logout")
				 .logoutSuccessUrl("/user/login?logout")
				 )
		 .userDetailsService(userDetailsService)
		 .csrf(csrf -> csrf.disable());
		 
		return http.build();
	}
}
