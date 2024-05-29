package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.QuizAnswer;

public interface QuizAnswerService {
	
	List<QuizAnswer> getAllQuizAnswers();
	
	List<QuizAnswer> getAllQuizAnswersByUserUsername(String username);
	
	QuizAnswer create(QuizAnswer quizAnswer);

}
