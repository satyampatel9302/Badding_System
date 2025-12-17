package com.baddingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.services.CategoryService;
import com.baddingSystem.services.ShopService;
import com.baddingSystem.util.AuthUtils;

@Controller
@RequestMapping("/admin/shop")
public class ShopController {

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AuthUtils authUtils;
	
	//Get Add shop 
	
	@GetMapping("/add")
	public String getAddShop(Model model) {
		Admin admin = authUtils.getCurrentAdmin();
		model.addAttribute("shop", new Shop());
		model.addAttribute("categories", categoryService.getAllCategory(admin));
		return "admin/shop-add";
	}
	
	//Save shop in database
	
	@PostMapping("/add")
	public String postAddShop(@ModelAttribute Shop shop) {
		Admin admin = authUtils.getCurrentAdmin();
		shopService.addShop(shop, admin);
		return "redirect:/admin/shop/all";
	}
	
	//Get all shop according to admin
	
	@GetMapping("/all")
	public String showAllShop(Model model) {
		Admin admin = authUtils.getCurrentAdmin();
		model.addAttribute("shops", shopService.findAllShop(admin));
		return "admin/shop-list";
	}
	
	//remove shop according to admin
	
	@GetMapping("/delete/{id}")
	public String removeShop(@PathVariable("id") Long id) {
		Admin admin = authUtils.getCurrentAdmin();
		shopService.removeShop(id, admin);
		return "redirect:/admin/shop/all";
	}
	
	//get update shop 
	
	@GetMapping("/edit/{id}")
	public String getEditShop(@PathVariable Long id, Model model) {
		Admin admin = authUtils.getCurrentAdmin();
		Shop shop = shopService.findByShopId(id);
		model.addAttribute("shop", shop);
		model.addAttribute("categories",categoryService.getAllCategory(admin));
		return "admin/shop-edit";
	}
	
	//Update shop and save in database
	
	@PostMapping("/update/{id}")
	public String postEditShop(@PathVariable Long id, 
			@ModelAttribute Shop shop) {
		Admin admin  = authUtils.getCurrentAdmin();
		shopService.updateShop(shop, admin, id);
		return "redirect:/admin/shop/all";
	}
}
