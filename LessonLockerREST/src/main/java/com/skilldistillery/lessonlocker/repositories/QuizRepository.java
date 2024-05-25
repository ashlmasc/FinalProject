package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{
	
	List<Quiz> getAllQuizByUserUsername(String username);
	

}
