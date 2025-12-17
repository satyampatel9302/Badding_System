package com.baddingSystem.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Category;
import com.baddingSystem.entityRoles.Role;
import com.baddingSystem.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	 // Save images inside resources (manual upload) OR switch to uploads folder if you want runtime uploads
    private final String uploadDir = "src/main/resources/static/categoryImg/";
	
    // Find Category by Category id
    
    public Optional<Category> getById(Long id){
    	return categoryRepository.findById(id);
    }
    
    // Find Category by Category Name
    
    public Optional<Category> findByName(String categoryName){
    	return categoryRepository.findByCategoryName(categoryName);
    }
    
    //Find all Categories
    
    public List<Category> getAllCategoryForUser(){
    	return categoryRepository.findAll();
    }

    //Find all Categories according to admin
    
    public List<Category> getAllCategory(Admin admin){
    	if (admin.getRole() == Role.MASTER) {
    		return categoryRepository.findAll();
    	} 
    		return categoryRepository.findByAdmin(admin);
    	
    }
    
    //Remove Category
    
    public void removeCategory (Admin admin, Long id) {
    	Category category = categoryRepository.findById(id)
    			.orElseThrow(()-> new RuntimeException("Category not found !!"));
    	if (admin.getRole() == Role.MASTER || category.getAdmin().getId().equals(admin.getId())) {
    		categoryRepository.delete(category);
    	} else {
    		throw new RuntimeException("UnAuthorized delete attempt !!");
    	}
    }
    
    //Add Category
    
    public void addCategory(Category category, Admin admin, MultipartFile file) throws IOException {
    	
    	if (file != null && !file.isEmpty()) {
    		String originalName = file.getOriginalFilename();
    		category.setCategory_img("/categoryImg/" + originalName);
    		
    		Path fileNameAndPath = Paths.get(uploadDir, originalName);
    		Files.createDirectories(fileNameAndPath.getParent());
    		Files.write(fileNameAndPath, file.getBytes());
    	}
    	category.setAdmin(admin);
    	categoryRepository.save(category);
    }
    
    //Update Category
    
    public void updateCategory(Category updateCategory, Admin admin, Long id , MultipartFile file) throws IOException {
    	Category existingCategory = categoryRepository.findById(id)
    			.orElseThrow(() ->new RuntimeException("Category Not Found !! "));
    	
    	if (admin.getRole() == Role.MASTER || existingCategory.getAdmin().getId().equals(admin.getId())) {
    		existingCategory.setCategoryName(updateCategory.getCategoryName());
    	
    	
    	if (file != null && !file.isEmpty()) {
    		String originalName = file.getOriginalFilename();
    		existingCategory.setCategory_img("/categoryImg/" + originalName);
    		
    		Path fileNameAndPath = Paths.get(uploadDir, originalName);
    		Files.createDirectories(fileNameAndPath.getParent());
    		Files.write(fileNameAndPath, file.getBytes());
    	}
    	
    	categoryRepository.save(existingCategory);
    }
    	else {
    		 new RuntimeException("Unthorized attempts !! ");
    	}
    }
}
