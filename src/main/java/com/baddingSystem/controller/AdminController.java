package com.baddingSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Bid;
import com.baddingSystem.entities.User;
import com.baddingSystem.repository.AdminRepository;
import com.baddingSystem.services.AdminService;
import com.baddingSystem.services.BidService;
import com.baddingSystem.services.EmailService;
import com.baddingSystem.services.UserService;
import com.baddingSystem.services.WinnerService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private WinnerService winnerService;
	
	 private User getUser(UserDetails userDetails) {
	        return userService.getUserByEmail(userDetails.getUsername()); // use UserService
	    }
	
	//Display admin welcome page 
	
	@GetMapping("/home")
	public String homePage() {
		return "admin/admin-welcome";
	}
	
	//Display login page for insert your data
	
	@GetMapping("/login")
	public String loginPage() {
		return "admin/admin-login";
	}
	
	//Display register page for insert your information
	
	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin/admin-register";
	}
	
	//Process your register information. 
	
	@PostMapping("/register")
	public String postRegisterPage(Model model, @ModelAttribute Admin admin) {
		if(!adminRepository.findByEmail(admin.getEmail()).isEmpty()) {
			model.addAttribute("error", "This email is already registered !");
			return "admin/admin-register";
		}
		
		adminService.registerAdmin(admin, false);
		 emailService.sendWelcomeEmail(admin.getEmail());
		return "redirect:/admin/login";
	}
	
	// Display dashboard page for show your actual details.
	
	@GetMapping("/dashboard")
	public String getDashboard(Model model, Principal principle) {
		String email = principle.getName();
		
		Admin admin = adminService.findByUserName(email);
		int totalCategories = admin.getCategory() != null ? admin.getCategory().size() : 0 ;
		int totalShops = admin.getShop() != null ? admin.getShop().size() : 0 ;
		model.addAttribute("admin", admin);
		model.addAttribute("totalCategories", totalCategories);
		model.addAttribute("shops", totalShops);
		return "admin/admin-dashboard";
	}
	
	//for logout page
	
	@GetMapping("/logout")
	public String logoutPage() {
		return "admin/admin-logout";
	}
	
	//Display history of all bids 
	
    @GetMapping("/bid/history/all")
    public String showAllBiddingHistory(Model model) {
        List<Bid> bids = bidService.getAllBiddingHistory();
        model.addAttribute("bids", bids);
        return "admin/all-bid-history"; // Thymeleaf template
    }
    
    @GetMapping("/winners")
    public String showWinners(Model model) {

        model.addAttribute("winners",
                winnerService.getAllWinners());

        return "admin/winners";
    }
	
}
