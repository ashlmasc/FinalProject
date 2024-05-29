package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skilldistillery.lessonlocker.entities.QuizAnswer;
import com.skilldistillery.lessonlocker.entities.QuizAnswerId;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, QuizAnswerId>{

	List<QuizAnswer> getAllQuizAnswersByUserUsername(String username);
	
	@Query("SELECT qa FROM QuizAnswer qa")
	List<QuizAnswer> getAllQuizAnswers();
	
}
