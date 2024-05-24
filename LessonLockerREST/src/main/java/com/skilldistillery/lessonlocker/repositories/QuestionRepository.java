package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.Question;

public interface QuestionRepository extends JpaRepository <Question, Integer>  {

	List<Question> getAllQuestionsByUserUsername(String username);
	
}
