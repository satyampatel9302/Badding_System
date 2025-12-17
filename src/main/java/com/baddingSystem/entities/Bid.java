package com.baddingSystem.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "bids")
public class Bid {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "bid_id")
	private Long id;
	
	@NotNull (message = "Please insert your bid amount !")
	private double amount;
	
	private LocalDateTime bidTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn (name = "shop_id")
	private Shop shop;

	//Getter and Setter 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getBidTime() {
		return bidTime;
	}

	public void setBidTime(LocalDateTime bidTime) {
		this.bidTime = bidTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	//All Arguments Constructor
	
	public Bid(Long id, @NotNull(message = "Please insert your bid amount !") double amount, LocalDateTime bidTime,
			User user, Shop shop) {
		super();
		this.id = id;
		this.amount = amount;
		this.bidTime = bidTime;
		this.user = user;
		this.shop = shop;
	}

	//No Arguments Constructor
	
	public Bid() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
