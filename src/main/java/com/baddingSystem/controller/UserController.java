package com.baddingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.User;
import com.baddingSystem.repository.UserRepository;
import com.baddingSystem.services.EmailService;
import com.baddingSystem.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	//Get User Register Page
	
	@GetMapping("/register")
	public String userGetRegister(Model model) {
		model.addAttribute("user", new User());
		return "user/register";
	}
	
	//Process data and save user in database
	
	@PostMapping("/register")
	public String userPostRegister(@ModelAttribute("user") User user, Model model) {
		
		if(!userRepository.findAllByEmail(user.getEmail()).isEmpty()) {
			model.addAttribute("emailError", "This email is already exist!!");
			return "user/register";
		}
	    userService.registerUser(user);
	    emailService.sendWelcomeEmail(user.getEmail());
	    return "redirect:/user/login?register=true";
	}
	
	//Get user login page
	
	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	}
	
	//Get login page if you are try to access authenticated page
	
	 @GetMapping("/please-login")
	    public String userPleaseLoginPage() {
	        return "redirect:/user/login";
	    } 
	 
	 //Get user profile details
	 
	 @GetMapping("/profile")
	    public String userProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	        User user = userService.getUserByEmail(userDetails.getUsername());
	        model.addAttribute("user", user);
	        return "user/profile";
	    }

	 //Return user logout page 
	 
	    @PostMapping("/custom-logout")
	    public String userLogout(@AuthenticationPrincipal UserDetails userDetails) {
	        User user = userService.getUserByEmail(userDetails.getUsername());
	        return "redirect:/login?logout";
	    }
}
