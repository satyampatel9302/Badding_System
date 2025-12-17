package com.baddingSystem.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Category;
import com.baddingSystem.services.CategoryService;
import com.baddingSystem.util.AuthUtils;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AuthUtils authUtils;
	
	//Add Category By user
	
	@GetMapping("/add")
	public String getAddCategory(Model model) {
		model.addAttribute("category", new Category());
		return "admin/add-category";
	}
	
	//Save category in database
	
	@PostMapping("/add")
	public String postAddCategory(
			@RequestParam ("categoryName") String categoryName,
			@RequestParam ("imageFile") MultipartFile file 
			) throws IOException {
		Admin admin = authUtils.getCurrentAdmin();
		Category category = new Category();
		category.setCategoryName(categoryName);
		categoryService.addCategory(category, admin, file);
		return "redirect:/admin/category/all";
	}
	
	//Get all category according to admin
	
	@GetMapping("/all")
	public String getAllCategory(Model model) {
		Admin admin = authUtils.getCurrentAdmin();
		model.addAttribute("categories", categoryService.getAllCategory(admin));
		return "admin/show-category";
	}
	
	//Remove category according to admin
	
	@GetMapping("delete/{id}")
	public String deleteCategory(@PathVariable ("id") Long id) {
		Admin admin = authUtils.getCurrentAdmin();
		categoryService.removeCategory(admin, id);
		return "redirect:/admin/category/all";
	}
	
	//Get update category date 
	
	@GetMapping("/update/{id}")
	public String getUpdateCategory(@PathVariable("id") Long id, Model model) {
		Category category = categoryService.getById(id)
				.orElseThrow(()-> new RuntimeException("Category not found !!"));
		model.addAttribute("category", category);
		return "admin/update-category";
	}
	
	//Update Category and save in database
	
	@PostMapping("/update/{id}")
	public String postUpdateCategory(
			@PathVariable("id") Long id,
			@RequestParam("categoryName") String categoryName,
			@RequestParam(value = "imgFile", required = false) MultipartFile file 
			) throws IOException {
		Admin admin = authUtils.getCurrentAdmin();
		Category category = new Category();
		category.setCategoryName(categoryName);
		categoryService.updateCategory(category, admin, id, file);
		return "redirect:/admin/category/all";
	}
}
