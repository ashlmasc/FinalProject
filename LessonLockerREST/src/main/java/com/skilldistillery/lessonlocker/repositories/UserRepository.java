package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skilldistillery.lessonlocker.entities.User;

public interface UserRepository extends JpaRepository <User, Integer> {
	
	User findByUsername(String username);
	
	User findById(int id);
	
	List<User> findAll();
	
//	@Query("SELECT u.username, u.firstName, u.lastName, u.enabled, u.cohort FROM User u order by u.id asc")
//	List<User> findAll();
	
	

}
