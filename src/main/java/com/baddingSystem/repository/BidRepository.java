package com.baddingSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baddingSystem.entities.Bid;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.entities.User;


public interface BidRepository extends JpaRepository<Bid, Long> {

	List<Bid> findByUser(User user);
	
	List<Bid> findAllByShop(Shop shop);
	
	   Optional<Bid> findTopByShopOrderByAmountDesc(Shop shop);

	    List<Bid> findByShopOrderByAmountDesc(Shop shop);

	
	    
	    List<Bid> findAllByOrderByBidTimeDesc(); // All bids, latest first
}
