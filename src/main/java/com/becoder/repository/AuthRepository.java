package com.becoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.model.User;

public interface AuthRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

}
