package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{
	
	List<Quiz> getAllQuizByUserUsername(String username);
	
	Question getById(int questionId);
	
	Quiz getQuizById(int quizId);
	
	Question getQuestionById(int questionId);
	
	List<Quiz> findAll();
	
}
