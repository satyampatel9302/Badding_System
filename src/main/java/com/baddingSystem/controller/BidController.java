package com.baddingSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baddingSystem.dto.BidMessage;
import com.baddingSystem.entities.Bid;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.entities.User;
import com.baddingSystem.repository.BidRepository;
import com.baddingSystem.services.BidService;
import com.baddingSystem.services.ShopService;
import com.baddingSystem.services.UserService;




@Controller
@RequestMapping("/user")
public class BidController {
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	//Placed bid 
	
	 @PostMapping("/bid/{shopId}")
	    public String placeBid(
	            @PathVariable Long shopId,
	            @RequestParam double amount,
	            @AuthenticationPrincipal UserDetails userDetails,
	            RedirectAttributes redirectAttributes
	    ) {

	        User user = userService.getUserByEmail(userDetails.getUsername());
	        Shop shop = shopService.findByShopId(shopId);

	        try {
	        Bid bid = bidService.placedBid(user, shop, amount);

	        // 🔔 GLOBAL ALERT (FOR ALL PAGES)
	        BidMessage alert = new BidMessage(
	                shop.getId(),
	                shop.getShopName(),
	                user.getName(),
	                bid.getAmount()
	        );

	        messagingTemplate.convertAndSend(
	                "/topic/bid-alerts",   // 👈 VERY IMPORTANT
	                alert
	        );
	        
	  
	        return "redirect:/user/shop/" + shopId;
	    
	        
	        } catch (RuntimeException e) {
	            if (e.getMessage().equals("Bidding closed")) {
	                redirectAttributes.addFlashAttribute("message", "Sorry! Bidding for this shop has already ended.");
	                return "redirect:/user/bidding-closed";
	            }
	            throw e; // rethrow other exceptions
	        }
	        
	        } 
	        
		
	//shop bidding closed
	
	 @GetMapping("/bidding-closed")
	    public String biddingClosedPage() {
	        return "user/bidding-closed"; 
	    }

	 
	 //get all shop bid history
	 
	 @GetMapping("/bid/history/{shopid}")
	 public String shopHistory(
			 @PathVariable Long shopid,
			 Model model
			 ) {
		 Shop  shop = shopService.findByShopId(shopid);
		 List<Bid> bids = bidService.getBiddingHistoryBySubCategory(shop);
		 
		 model.addAttribute("shop", shop);
		 model.addAttribute("bids", bids);
		 
		 return "user/bid-history";
	 }
	 
	 //get all user bids
	 
	 @GetMapping("/my-bids")
	 public String getUserAllBids(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		 User user = userService.getUserByEmail(userDetails.getUsername());
		 List<Bid> bids = bidService.getBidsByUser(user);
		 model.addAttribute("bids", bids);
		 model.addAttribute("user", user);
		 model.addAttribute("totalBids", bids.size());
		 return "user/my-bids";
	 }
	 
	 //Get specific shop winners
	 
	   @GetMapping("/shop/{shopId}/winner")
	    public String showWinner(
	            @PathVariable Long shopId,
	            Model model
	    ) {
	        Shop shop = shopService.findByShopId(shopId);

	        Optional<Bid> winnerBid = bidService.getWinner(shop);

	        if (winnerBid.isPresent()) {
	            model.addAttribute("winnerBid", winnerBid.get());
	            model.addAttribute("shop", shop);
	        } else {
	            model.addAttribute("message", "Bidding is still open or no bids placed yet!");
	            model.addAttribute("shop", shop);
	        }

	        return "user/winner"; // Thymeleaf template
	    }

	    //Return all winners
	   
	    @GetMapping("/winners")
	    public String showAllWinners(Model model) {
	        List<Shop> winners = bidService.getAllWinners();
	        model.addAttribute("winners", winners);
	        return "user/all-winners"; // Thymeleaf template
	    }

	    
	    //testing
	    
	  /*  @GetMapping("/admin/declare-winner/{shopid}")
	    @ResponseBody
	    public String declareWinner(@PathVariable Long shopid) {
	        Shop shop = shopService.findByShopId(shopid);
	        bidService.declareWinnerNow(shop);
	        return "Winner declared and email sent!";
	    }*/
}
