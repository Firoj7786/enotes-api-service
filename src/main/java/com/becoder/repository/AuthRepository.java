package com.becoder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.model.User;

public interface AuthRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	User findByEmail(String username);

	User findById(Long userId);

}
