package com.skilldistillery.lessonlocker.services;

import com.skilldistillery.lessonlocker.entities.Question;

public interface QuestionService {
	
    Question getQuestionById(int id);
    Question createQuestion(Question question);
    Question updateQuestion(int id, Question question);
    boolean deleteQuestion(int id);

}
