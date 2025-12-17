package com.baddingSystem.entities;

import java.util.List;

import com.baddingSystem.entityRoles.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "admins")
public class Admin {
	
	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "user_name")
	@NotNull (message = "Name should not be empty !!")
	private String username;
	
	@Column (name = "email")
	@NotNull (message = "Email Should not be empty !!")
	private String email;
	
	@Column (name = "password")
	@Size (min = 4, message = "Password must be contains 4 digits and characters !")
	private String password;
	
	@Enumerated (EnumType.STRING)
	private Role role;
	
	@OneToMany (mappedBy = "admin")
	private List<Category> category;
	
	@OneToMany (mappedBy = "admin")
	private List<Shop> shop;

	//Getter and Setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public List<Shop> getShop() {
		return shop;
	}

	public void setShop(List<Shop> shop) {
		this.shop = shop;
	}

	//All Arguments Constructor
	
	public Admin(Long id, @NotNull(message = "Name should not be empty !!") String username,
			@NotNull(message = "Email Should not be empty !!") String email,
			@Size(min = 4, message = "Password must be contains 4 digits and characters !") String password, Role role,
			List<Category> category, List<Shop> shop) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.category = category;
		this.shop = shop;
	}

	//No Argument Constructor
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
