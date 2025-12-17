package com.baddingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Category;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.entityRoles.Role;
import com.baddingSystem.services.AdminService;

@Controller
@RequestMapping("/admin/master")
public class MasterAdminController {
	
	@Autowired
	private AdminService adminService;
	
	//show admin view dashboard with all details 
	
	@GetMapping("/view/{id}")
	public String adminDashboard(Model model, @PathVariable Long id) {
		Admin admin = adminService.findById(id);
		
		if (admin == null) {
			return "redirect:/admin/master/manage";
		}
		
		List<Category> categories = admin.getCategory();
		List<Shop> shops = admin.getShop();
		
		model.addAttribute("admin", admin);
		model.addAttribute("categories", categories);
		model.addAttribute("shops", shops);
		model.addAttribute("totalCategories", categories != null ? categories.size() : 0);
		model.addAttribute("totalShops", shops != null ? shops.size() : 0);
		return "admin/admin-viewDashboard";
	}
	
	//Manage admins
	
	@GetMapping("/manage")
	public String manageAdminPage(Model model) {
		model.addAttribute("admins", adminService.getAllAdmins());
		return "admin/admin-manage";
	}
	
	//Delete admins
	
	@GetMapping("/delete/{id}")
	public String deleteAdmin(@PathVariable Long id, Model model) {
		try {
			adminService.removeAdmin(id);
			return "redirect:/admin/master/manage";
		} catch(Exception e) {
	        model.addAttribute("errorMessage", 
	                "⚠️ Please clear assigned categories/products before deleting this admin.");
	            return "admin/admin-delete-error";
	        }
		}
	
	// Update role form NORMAL -> MASTER
	
	@GetMapping("/promote/{id}")
	public String promptAdmin(@PathVariable Long id) {
		adminService.updateRole(id, Role.MASTER);
		return "redirect:/admin/master/manage";
	}
	
	// Update role form MASTER -> NORMAL
	
	@GetMapping("/demote/{id}")
	public String demoteAdmin(@PathVariable Long id) {
		adminService.updateRole(id, Role.NORMAL);
		return "redirect:/admin/master/manage";
	}
	
}
