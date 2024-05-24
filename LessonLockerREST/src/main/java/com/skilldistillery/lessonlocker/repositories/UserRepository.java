package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.User;

public interface UserRepository extends JpaRepository <User, Integer> {
	
	User findByUsername(String username);
	
	List<User> findAll();

}
