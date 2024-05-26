package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;

public interface QuizService {
	
	List<Quiz> getAllQuizByUserUsername(String username);
	
	Quiz create(Quiz quiz);
	
	// QuizQuestion addQuestionToQuiz(int quizId, int questionId);
	
//	Question getById(int questionId);
	
//	QuizQuestion createQuizQuestion(QuizQuestion quizQuestion, String username);

}
