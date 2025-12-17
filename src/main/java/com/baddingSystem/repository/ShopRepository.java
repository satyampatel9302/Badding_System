package com.baddingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baddingSystem.entities.Admin;
import com.baddingSystem.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
	
	//find Shop according to category.
	
	List<Shop> findByCategoryId (Long id);
	
	//find a shop according to admin.
	
	List<Shop> findByAdmin (Admin admin);

}
