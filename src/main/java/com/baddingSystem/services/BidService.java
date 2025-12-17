package com.baddingSystem.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baddingSystem.dto.BidMessage;
import com.baddingSystem.entities.Bid;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.entities.User;
import com.baddingSystem.entities.Winner;
import com.baddingSystem.repository.BidRepository;
import com.baddingSystem.repository.ShopRepository;
import com.baddingSystem.repository.UserRepository;
import com.baddingSystem.repository.WinnerRepository;


@Service
public class BidService {

	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private WinnerRepository winnerRepository;
	
	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private SimpMessagingTemplate simplerMessagingTemplate;
	
	public Bid placedBid(User user, Shop shop, double amount) {
		
		if(amount < shop.getMinBid()) {
			 throw new RuntimeException("Minimum bid is ₹" + shop.getMinBid());
		}
		
		Optional<Bid> previousHighest = bidRepository.findTopByShopOrderByAmountDesc(shop);
		
		if(previousHighest.isPresent() && amount <= previousHighest.get().getAmount()) {
			throw new RuntimeException("Bid must be higher then current bid !");
		}
	
		  LocalDate today = LocalDate.now();
	        if (today.isBefore(shop.getStartDate()) ||
	            today.isAfter(shop.getEndDate())) {
	            throw new RuntimeException("Bidding closed");
	        }
		
		Bid bid = new Bid();
		bid.setUser(user);
		bid.setShop(shop);
		bid.setAmount(amount);
		bid.setBidTime(LocalDateTime.now());
		
		Bid bidSave = bidRepository.save(bid);
		
		emailService.sendBidConfirmationEmail(user, shop, bidSave);
		
		  List<User> allUsers = userRepository.findAll();

	        for (User u : allUsers) {

	            // bidder ko alert nahi bhejna
	            if (u.getId().equals(user.getId())) {
	                continue;
	            }

	            emailService.sendBidAlertEmail(
	                    u,
	                    user,          // bidder
	                    shop,
	                    bidSave.getAmount()
	            );
	        }
		
		BidMessage message = new BidMessage();
		message.setShopId(shop.getId());
		message.setShopName(shop.getShopName());
		message.setBidderName(user.getName());
		message.setAmount(bid.getAmount());
		
		simplerMessagingTemplate.convertAndSend("/topic/bid-alerts", message);
	    return bidSave;
	}
	
	 public double getCurrentHighestBid(Shop shop) {
	        return bidRepository.findTopByShopOrderByAmountDesc(shop)
	                .map(Bid::getAmount)
	                .orElse(shop.getMinBid());
	    }
	 
	 public Optional<Bid> gitHighestBid(Shop shop){
		 return bidRepository.findTopByShopOrderByAmountDesc(shop);
	 }
	 
	  // ✅ Logged-in user ki saari bids
	    public List<Bid> getBidsByUser(User user) {
	        return bidRepository.findByUser(user);
	    }
	    
	    /** 🔥 NEW: Get winner bid for a shop if bidding has ended */
	    public Optional<Bid> getWinner(Shop shop) {
	        LocalDate today = LocalDate.now();
	        if (today.isAfter(shop.getEndDate())) {
	            return bidRepository.findTopByShopOrderByAmountDesc(shop);
	        }
	        return Optional.empty();
	    }
	    
	    
	    
	    public List<Shop> getAllWinners() {
	        // Get all subcategories
	        List<Shop> allShops = shopRepository.findAll();

	        // Filter shops that have ended and have a winnerBid
	        return allShops.stream()
	                .filter(shop -> shop.getEndDate() != null)                     // endDate exists
	                .filter(shop -> !shop.getEndDate().isAfter(LocalDate.now()))  // bidding ended
	                .filter(shop -> shop.getWinnerBid() != null)                  // winner exists
	                .collect(Collectors.toList());
	    }
	    

	    
	/*    //for immediate winner testing
	    @Transactional  // ✅ Important
	    public void declareWinnerNow(Shop shop) {
	        if (shop.getWinnerBid() == null && LocalDate.now().isAfter(shop.getEndDate())) {
	            bidRepository.findTopByShopOrderByAmountDesc(shop)
	                .ifPresent(bid -> {
	                    shop.setWinnerBid(bid); // set winner
	                    shopRepository.save(shop);   // save to DB

	                    // send email
	                //    emailService.sendWinnerEmail(bid.getUser(), shop, bid);
	                    System.out.println("Winner declared: " + bid.getUser().getName());
	                });
	        }
	    } */
	    
	

	    @Transactional
	    public void declareWinner() {

	        List<Shop> shops = shopRepository.findAll();

	        for (Shop shop : shops) {

	            if (LocalDate.now().isAfter(shop.getEndDate())
	                    && !winnerRepository.existsByShop(shop)) {

	                bidRepository.findTopByShopOrderByAmountDesc(shop)
	                    .ifPresent(bid -> {

	                        Winner winner = new Winner();
	                        winner.setShop(shop);
	                        winner.setUser(bid.getUser());
	                        winner.setBid(bid);
	                        winner.setDeclaredDate(LocalDate.now());

	                        winnerRepository.save(winner);

	                        // optional legacy reference
	                        shop.setWinnerBid(bid);
	                        shopRepository.save(shop);

	                        emailService.sendWinnerEmail(
	                                bid.getUser(),
	                                shop,
	                                bid
	                        );

	                        System.out.println(
	                            "Winner declared → " + bid.getUser().getName()
	                        );
	                    });
	            }
	        }
	    }

	    
	    
	    public List<Bid> getBiddingHistoryBySubCategory(Shop shop) {
	        return bidRepository.findByShopOrderByAmountDesc(shop);
	    }
	    
	    public List<Bid> getAllBiddingHistory() {
	        return bidRepository.findAllByOrderByBidTimeDesc();
	    }
}
