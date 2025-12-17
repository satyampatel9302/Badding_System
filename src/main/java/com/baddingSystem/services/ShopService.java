package com.baddingSystem.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.entityRoles.Role;
import com.baddingSystem.repository.ShopRepository;

@Service
public class ShopService {
	
	@Autowired
	private ShopRepository shopRepository;
	
	//Find shop by Shop id
	
	public Shop findByShopId(Long id) {
		return shopRepository.findById(id).orElseThrow(null);
	}
	
	//find all Shops
	
	public List<Shop> findAllShopForUser () {
		return shopRepository.findAll();
	}
	
	//Find all Shops according to admin
	
	public List<Shop> findAllShop(Admin admin){
		if(admin.getRole() == Role.MASTER) {
			return shopRepository.findAll();
		} 
		return shopRepository.findByAdmin(admin);
	}
	
	//Remove Shop
	
	public void removeShop(Long id, Admin admin) {
		Shop shop = shopRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Shop not found !!"));
		if(admin.getRole() == Role.MASTER || shop.getAdmin().getId().equals(admin.getId())) {
			shopRepository.delete(shop);
		} else {
			new RuntimeException("Unwanted attempts !!");
		}
	}
	
	//Add Shop with primary and multiple images.
	
	public Shop addShop(Shop shop, Admin admin) {
		
		MultipartFile primaryImg = shop.getPrimaryImgFile();
		
		//Set single primary image. 
		
		if(primaryImg!=null &&!primaryImg.isEmpty()) {
			String originalName = primaryImg.getOriginalFilename();
			String path = "/shopImg/" + originalName;
			shop.setShop_primaryImg(path);
		}
		
		//set multiple images.
		
		List<String> imgPaths = new ArrayList<>();
		if (shop.getShopImgFiles() !=null ) {
			for (MultipartFile file : shop.getShopImgFiles()) {
				if (!file.isEmpty()) {
	                String originalName = file.getOriginalFilename();
	                String path = "/shopImg/" + originalName;
	                imgPaths.add(path);
	            }
			}
		}
		
		shop.setShopImg(imgPaths);
		shop.setAdmin(admin);
		return shopRepository.save(shop);
	}

	//Update shop 
	
	public Shop updateShop (Shop shop, Admin admin, Long id) {
		
		Shop existingShop = shopRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Shop not found!!"));
		
		existingShop.setCategory(shop.getCategory());
		existingShop.setDescription(shop.getDescription());
		existingShop.setEndDate(shop.getEndDate());
		existingShop.setMinBid(shop.getMinBid());
		existingShop.setStartDate(shop.getStartDate());
		existingShop.setShopName(shop.getShopName());
				
		//Update single primary image.
		
	    MultipartFile newPrimaryImg = shop.getPrimaryImgFile();

	    if (newPrimaryImg != null && !newPrimaryImg.isEmpty()) {

	        String originalName = newPrimaryImg.getOriginalFilename();
	        String path = "/shopImg/" + originalName;

	        existingShop.setShop_primaryImg(path);
	    }

	    //Update all multiple images.
	    
	    List<String> updatedImgList = new ArrayList<>();

	    if (shop.getShopImgFiles() != null) {

	        for (MultipartFile file : shop.getShopImgFiles()) {

	            if (!file.isEmpty()) {
	                String name = file.getOriginalFilename();
	                String path = "/subcategoryImg/" + name;
	                updatedImgList.add(path);
	            }
	        }
	    }

	    // If new images list is NOT empty → replace old
	    
	    if (!updatedImgList.isEmpty()) {
	        existingShop.setShopImg(updatedImgList);
	    }
	    return shopRepository.save(existingShop);
	}
}
