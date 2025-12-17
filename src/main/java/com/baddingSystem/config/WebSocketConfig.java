package com.baddingSystem.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.baddingSystem.entities.Shop;
import com.baddingSystem.repository.BidRepository;
import com.baddingSystem.repository.ShopRepository;
import com.baddingSystem.services.EmailService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("bid-ws")
		.setAllowedOriginPatterns("*")
		.withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}

    public void declareWinner() {
    	List<Shop> shop = shopRepository.findAll();
    	
    	for (Shop s : shop) {
    		if (s.getWinnerBid() == null && LocalDate.now().isAfter(s.getEndDate())) {
    			 bidRepository.findTopByShopOrderByAmountDesc(s)
                 .ifPresent(bid -> {
                     s.setWinnerBid(bid); // store winner
                     shopRepository.save(s);
                     
                     // 🔥 SEND WINNER EMAIL
                     emailService.sendWinnerEmail(bid.getUser(), s, bid);
                     
                     System.out.println("Winner declared: " + bid.getUser().getName());
                     
                 });
    		}
    	}
    }
}
