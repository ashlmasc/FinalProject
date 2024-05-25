package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.repositories.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {
	
	private QuizRepository quizRepo;
	
	public QuizServiceImpl(QuizRepository quizRepo) {
        super();
        this.quizRepo = quizRepo;
    }

	@Override
	public List<Quiz> getAllQuizByUserUsername(String username) {
		List<Quiz> questions = quizRepo.getAllQuizByUserUsername(username);
		return questions;
	}

}
