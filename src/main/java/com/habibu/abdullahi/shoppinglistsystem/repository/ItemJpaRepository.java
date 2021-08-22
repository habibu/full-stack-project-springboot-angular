package com.habibu.abdullahi.shoppinglistsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.habibu.abdullahi.shoppinglistsystem.dto.ItemDTO;


@Component
public interface ItemJpaRepository extends JpaRepository<ItemDTO, Long> {
    
    ItemDTO findById(Long id);
    ItemDTO findByProductname(String productname);
}
