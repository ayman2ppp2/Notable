package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.User;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
	
}