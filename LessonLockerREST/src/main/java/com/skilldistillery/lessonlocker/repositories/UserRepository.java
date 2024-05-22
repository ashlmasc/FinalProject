package com.skilldistillery.lessonlocker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.User;

public interface UserRepository extends JpaRepository <User, Integer> {
	
	User findByUsername(String username);

}
