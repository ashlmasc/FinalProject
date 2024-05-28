package com.skilldistillery.lessonlocker.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.QuizQuestion;

public interface QuizQuestionRepository  extends JpaRepository<QuizQuestion, Integer>{
	
		List<QuizQuestion> getAllQuestionsByQuizUserUsernameAndQuizId(String username, int id);
		
		QuizQuestion getByQuizIdAndQuestionId(int quizId, int questionId);


}
