package com.baddingSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Category;

public interface CategoryRepository extends JpaRepository <Category, Long>{

	//Find Category by its Name.
	
	Optional<Category> findByCategoryName (String categoryName);
	
	//Find Category according to admin.
	
	List<Category> findByAdmin (Admin admin);
}
