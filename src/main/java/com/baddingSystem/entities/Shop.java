package com.baddingSystem.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;



import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table (name = "shops")
public class Shop {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "shop_id")
	private Long id;
	
	@Column (name = "shop_name")
	private String shopName;
	
	@Column (name = "shop_description")
	private String description;
	
	@Column (name = "shop_minbid")
	private double minBid;
	
	@Column (name = "start_date")
	private LocalDate startDate;
	
	@Column (name = "end_date")
	private LocalDate endDate;
	
	//store primary single image..
	
	@Column (name = "shop_primaryimg")
	private String shop_primaryImg;
	
	//Store multipal Images for more details..
	
	@ElementCollection
	@CollectionTable (
			name = "shop_img",
			joinColumns = @JoinColumn (name = "shop_id")
			)
	@Column (name = "shopimg_path")
	private List<String> shopImg = new ArrayList<>();
	
	//One Category is allow many shop ...
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "category_id", referencedColumnName = "category_id")
	private Category category;
	
	//One admin are allow many shops..
	
	@ManyToOne
	@JoinColumn (name = "admin_id")
	private Admin admin;
	
	@Transient
	private MultipartFile primaryImgFile;
	
	@Transient
	private List<MultipartFile> shopImgFiles;
	
	@ManyToOne
	@JoinColumn(name = "bid_id")
	private Bid winnerBid;



	//Getter and Setter
	
	public void setWinnerBid(Bid bid) {
	    this.winnerBid = bid;
	}
	
	public Bid getWinnerBid() {
		return winnerBid;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMinBid() {
		return minBid;
	}

	public void setMinBid(double minBid) {
		this.minBid = minBid;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getShop_primaryImg() {
		return shop_primaryImg;
	}

	public void setShop_primaryImg(String shop_primaryImg) {
		this.shop_primaryImg = shop_primaryImg;
	}

	public List<String> getShopImg() {
		return shopImg;
	}

	public void setShopImg(List<String> shopImg) {
		this.shopImg = shopImg;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public MultipartFile getPrimaryImgFile() {
		return primaryImgFile;
	}

	public void setPrimaryImgFile(MultipartFile primaryImgFile) {
		this.primaryImgFile = primaryImgFile;
	}

	public List<MultipartFile> getShopImgFiles() {
		return shopImgFiles;
	}

	public void setShopImgFiles(List<MultipartFile> shopImgFiles) {
		this.shopImgFiles = shopImgFiles;
	}

	//All Arguments Constructor
	
	public Shop(Long id, String shopName, String description, double minBid, LocalDate startDate, LocalDate endDate,
			String shop_primaryImg, List<String> shopImg, Category category, Admin admin, MultipartFile primaryImgFile,
			List<MultipartFile> shopImgFiles) {
		super();
		this.id = id;
		this.shopName = shopName;
		this.description = description;
		this.minBid = minBid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shop_primaryImg = shop_primaryImg;
		this.shopImg = shopImg;
		this.category = category;
		this.admin = admin;
		this.primaryImgFile = primaryImgFile;
		this.shopImgFiles = shopImgFiles;
	}

	//No Arguments Constructor
	
	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
