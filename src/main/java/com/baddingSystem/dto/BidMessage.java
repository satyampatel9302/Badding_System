package com.baddingSystem.dto;


public class BidMessage {

    private Long shopId;
    private String shopName;
    private String bidderName;
    private double amount;

    public BidMessage(Long shopId, String shopName,
                      String bidderName, double amount) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.bidderName = bidderName;
        this.amount = amount;
    }

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
    
	  public BidMessage() {
	    }

    // getters & setters
}
 
    


