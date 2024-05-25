package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.Quiz;

public interface QuizService {
	
	List<Quiz> getAllQuizByUserUsername(String username);

}
