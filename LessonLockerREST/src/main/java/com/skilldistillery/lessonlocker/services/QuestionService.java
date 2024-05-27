package com.skilldistillery.lessonlocker.services;

import java.util.List;

import com.skilldistillery.lessonlocker.entities.Question;

public interface QuestionService {
	
	List<Question> getAllQuestionsByIsEnabled(Boolean isEnabled);

}
