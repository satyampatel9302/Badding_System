package com.baddingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baddingSystem.entities.Shop;
import com.baddingSystem.entities.Winner;

public interface WinnerRepository extends JpaRepository<Winner, Long> {

    boolean existsByShop(Shop shop);

    List<Winner> findAllByOrderByDeclaredDateDesc();
}
