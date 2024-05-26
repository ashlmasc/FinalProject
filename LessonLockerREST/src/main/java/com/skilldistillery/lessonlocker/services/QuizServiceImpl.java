package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.repositories.QuizQuestionRepository;
import com.skilldistillery.lessonlocker.repositories.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {
	
	private QuizRepository quizRepo;
	private QuizQuestionRepository quizQuestionRepo;
	
	public QuizServiceImpl(QuizRepository quizRepo, QuizQuestionRepository quizQuestionRepo) {
        super();
        this.quizRepo = quizRepo;
        this.quizQuestionRepo = quizQuestionRepo;
    }

	@Override
	public List<Quiz> getAllQuizByUserUsername(String username) {
		List<Quiz> questions = quizRepo.getAllQuizByUserUsername(username);
		return questions;
	}

	@Override
	public Quiz create(Quiz quiz) {
		Quiz newQuiz = null;
		newQuiz = quizRepo.saveAndFlush(quiz);
		return newQuiz;
	}

//	@Override
//	public Question getById(int questionId) {
//		Question question = quizRepo.getById(questionId);
//		return question;
//	}

//	@Override
//	public QuizQuestion createQuizQuestion(QuizQuestion quizQuestion, String username) {
//		QuizQuestion newQuizQuestion = null;
//		newQuizQuestion = quizQuestionRepo.saveAndFlush(quizQuestion);
//		return newQuizQuestion;
//	}

}
