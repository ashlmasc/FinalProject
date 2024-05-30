package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.Question;


public interface StudentService {
	
	List<Question> getAllQuestions(String username);
	
	Question show(String username, int id);
	
}
