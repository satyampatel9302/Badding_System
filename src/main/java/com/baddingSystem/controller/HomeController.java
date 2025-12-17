package com.baddingSystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baddingSystem.entities.Bid;
import com.baddingSystem.entities.Category;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.services.BidService;
import com.baddingSystem.services.CategoryService;
import com.baddingSystem.services.ShopService;

@Controller
@RequestMapping("/user")
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private BidService bidService;
	
	//Get User Home page with All Categories
	
	@GetMapping("/home")
	public String userHome(Model model) {
		model.addAttribute("categories", categoryService.getAllCategoryForUser());
		return "user/home";
	}
	
	//return shop according to category
	
	@GetMapping("/category/{id}")
	public String categoryWithProduct(@PathVariable Long id , Model model) {
		Category category = categoryService.getById(id).orElse(null);
		List<Shop> shop = shopService.findAllShopForUser()
				.stream()
				.filter(catShop -> catShop.getCategory().getId().equals(id))
				.toList();
				
				Map<Long, Double> displayBids = new HashMap<>();
				
				for (Shop s : shop) {
					double currentBid = bidService.getCurrentHighestBid(s);
					displayBids.put(s.getId(), currentBid);
				}
				
				model.addAttribute("category", category);
				model.addAttribute("shops", shop);
				model.addAttribute("displayBids", displayBids);
				return "user/categoryShops";
	}
	
	//Return All shops
	
	@GetMapping("/shop")
	public String userShop(Model model) {
		List<Shop> shop = shopService.findAllShopForUser();
		
		Map<Long, Double > displayBids = new HashMap<>();
		for (Shop s : shop) {
			double currentBid = bidService.getCurrentHighestBid(s);
			displayBids.put(s.getId(), currentBid);
		}
		
		model.addAttribute("shops", shop );
		model.addAttribute("displayBids", displayBids);
		return "user/shop";
	}
	
	//Return shop details
	
	@GetMapping("/shop/{id}")
	public String viewShop(@PathVariable("id") Long id, Model model) {
		Shop shop = shopService.findByShopId(id);
		
		if(shop == null) {
			return "redirect:/user/shop?notfound=true";
		}
		
		Optional<Bid> higestBid = bidService.gitHighestBid(shop);
		model.addAttribute("shop", shop);
		model.addAttribute("highestBid", higestBid.orElse(null));
		return "user/shop-view";
	}
	
}
