package com.baddingSystem.entities;

import com.baddingSystem.entityRoles.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "users")
public class User {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Name should not be empty!")
	@Column (name = "name")
	private String name;
	
	@NotNull (message = "Email should not be empty!")
	@Column (name = "email")
	private String email;
	
	@Size (min = 4, message = "Password must be contains atleast 4 character and numbers")
	@Column (name = "password")
	private String password;
	
	@Enumerated (EnumType.STRING)
	private Role role;

	//Getter & Setter for private data access
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	//All Arguments Constructor
	
	public User(Long id, @NotNull(message = "Name should not be empty!") String name,
			@NotNull(message = "Email should not be empty!") String email,
			@Size(min = 4, message = "Password must be contains atleast 4 character and numbers") String password,
			Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	//No Arguments Constructor
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
