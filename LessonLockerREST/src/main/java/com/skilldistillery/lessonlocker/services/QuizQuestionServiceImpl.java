package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.repositories.QuizQuestionRepository;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {
	
	
	private QuizQuestionRepository quizQuestionRepo;
	
	public QuizQuestionServiceImpl(QuizQuestionRepository quizQuestionRepo) {
		super();
		this.quizQuestionRepo = quizQuestionRepo;
	}

	@Override
	public List<QuizQuestion> questionsForQuizId(String username, int id) {
		return quizQuestionRepo.getAllQuestionsByQuizUserUsernameAndQuizId(username, id);
	}

	@Override
	public QuizQuestion create(QuizQuestion question) {
		return quizQuestionRepo.saveAndFlush(question);
	}


}
