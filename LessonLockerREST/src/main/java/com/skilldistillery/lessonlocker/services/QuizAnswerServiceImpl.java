package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.QuizAnswer;
import com.skilldistillery.lessonlocker.repositories.QuizAnswerRepository;

@Service
public class QuizAnswerServiceImpl implements QuizAnswerService {

	private QuizAnswerRepository quizAnswerRepo;
	
	public QuizAnswerServiceImpl(QuizAnswerRepository quizAnswerRepo) {
		super();
		this.quizAnswerRepo = quizAnswerRepo;
	}

	@Override
	public List<QuizAnswer> getAllQuizAnswersByUserUsername(String username) {
		return quizAnswerRepo.getAllQuizAnswersByUserUsername(username);
	}

	@Override
	public QuizAnswer create(QuizAnswer quizAnswer) {
		return quizAnswerRepo.saveAndFlush(quizAnswer);
	}


	
}
