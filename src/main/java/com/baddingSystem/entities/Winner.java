package com.baddingSystem.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "winners")
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // one user can win many times
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // one shop has one winner
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    // winning bid
    @ManyToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    private LocalDate declaredDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public LocalDate getDeclaredDate() {
		return declaredDate;
	}

	public void setDeclaredDate(LocalDate declaredDate) {
		this.declaredDate = declaredDate;
	}

    // getters & setters
    
    
    
}
