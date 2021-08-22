package com.habibu.abdullahi.shoppinglistsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.habibu.abdullahi.shoppinglistsystem.dto.UserDTO;

@Component
public interface UserJpaRepository extends JpaRepository<UserDTO, Long> {
    
    UserDTO findById(Long id);
    UserDTO findByName(String name);
}
