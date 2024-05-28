package com.skilldistillery.lessonlocker.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.Question;

public interface QuestionRepository extends JpaRepository <Question, Integer>  {
	
	List<Question> getAllQuestionsByEnabled(Boolean isEnabled);

	List<Question> getAllQuestionsByUserUsername(String username);
	
	Question getQuestionByUserUsernameAndId(String username, int id);
	
	Optional<Question> findById(int id);
    
}
