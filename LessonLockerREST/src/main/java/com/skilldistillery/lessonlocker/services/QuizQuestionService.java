package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.QuizQuestion;

@Service
public interface QuizQuestionService {
	
	List<QuizQuestion> questionsForQuizId(String username, int id);
	
	QuizQuestion create(QuizQuestion question);
	
	QuizQuestion getByQuizIdAndQuestionId(int quizId, int questionId);
	

}
