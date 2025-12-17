package com.baddingSystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "category")
public class Category {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "category_id")
	private Long id;
	
	@Column (name = "category_name")
	private String categoryName;
	
	@Column (name = "category_img")
	private String Category_img;
	
	//Add a relation for access many category by single Admin.
	
	@ManyToOne
	@JoinColumn (name = "admin_id")
	private Admin admin;

	//Getter & Setter for private data access
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategory_img() {
		return Category_img;
	}

	public void setCategory_img(String category_img) {
		Category_img = category_img;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	//All Arguments constructor
	
	public Category(Long id, String categoryName, String category_img, Admin admin) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		Category_img = category_img;
		this.admin = admin;
	}

	//No arguments constructor
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
