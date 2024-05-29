package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.repositories.QuizQuestionRepository;
import com.skilldistillery.lessonlocker.repositories.QuizRepository;
import com.skilldistillery.lessonlocker.repositories.UserRepository;

@Service
public class QuizServiceImpl implements QuizService {
	
	private QuizRepository quizRepo;
	private QuizQuestionRepository quizQuestionRepo;
	private UserRepository userRepo;
	
	public QuizServiceImpl(QuizRepository quizRepo, QuizQuestionRepository quizQuestionRepo, UserRepository userRepo) {
        super();
        this.quizRepo = quizRepo;
        this.quizQuestionRepo = quizQuestionRepo;
        this.userRepo = userRepo;
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

	@Override
	public Quiz getQuizById(String username, int id) {
		
		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			return null;
		}
		
		Quiz quiz = quizRepo.getQuizById(id);
        return quiz;
	}

	@Override
	public Question getQuestionById(int questionId) {
		Question question = quizRepo.getQuestionById(questionId);
		return question;
	}

	@Override
	public Quiz getById(int id) {
		Quiz quiz = quizRepo.findById(id).orElse(null);
		return quiz;
	}

	@Override
	public List<Quiz> getAllQuiz() {
		List<Quiz> quizzes = quizRepo.findAll();
		return quizzes;
	}

//	@Override
//	public QuizQuestion createQuizQuestion(QuizQuestion quizQuestion, String username) {
//		QuizQuestion newQuizQuestion = null;
//		newQuizQuestion = quizQuestionRepo.saveAndFlush(quizQuestion);
//		return newQuizQuestion;
//	}

}
