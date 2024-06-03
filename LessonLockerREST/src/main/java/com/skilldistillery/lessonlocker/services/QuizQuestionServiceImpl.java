package com.skilldistillery.lessonlocker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.repositories.QuestionRepository;
import com.skilldistillery.lessonlocker.repositories.QuizQuestionRepository;
import com.skilldistillery.lessonlocker.repositories.QuizRepository;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {
	
	
	private QuizQuestionRepository quizQuestionRepo;
	private QuestionRepository questionRepo;
	private QuizRepository quizRepo;

	
	public QuizQuestionServiceImpl(QuizQuestionRepository quizQuestionRepo, QuizRepository quizRepo, QuestionRepository questionRepo) {
		super();
		this.quizQuestionRepo = quizQuestionRepo;
		this.quizRepo = quizRepo;
		this.questionRepo = questionRepo;

	}

	@Override
	public List<QuizQuestion> questionsForQuizId(String username, int id) {
		return quizQuestionRepo.getAllQuestionsByQuizUserUsernameAndQuizId(username, id);
	}

	@Override
	public QuizQuestion create(QuizQuestion question) {
		return quizQuestionRepo.saveAndFlush(question);
	}

	@Override
	public QuizQuestion getByQuizIdAndQuestionId(int quizId, int questionId) {
		return quizQuestionRepo.getByQuizIdAndQuestionId(quizId, questionId);
	}

	@Override
	public QuizQuestion addQuestionToQuiz(String Username, int quizId, int questionId) {
		QuizQuestion quizQuestion = null;
		
		Quiz foundQuiz = quizRepo.getQuizById(quizId);
		
		if (foundQuiz == null) {
			return null;
		}

		Optional<Question> optSelectedQuestion = questionRepo.findById(questionId);
		
		Question question = null;
		
		if (optSelectedQuestion.isPresent()) {
             question = optSelectedQuestion.get();
		
        }
		
		if (question == null) {
			return null;
		}
		
		// does quiz question already exist ?
		
		QuizQuestion wasFound = quizQuestion = quizQuestionRepo.getByQuizIdAndQuestionId(quizId, questionId);
		
		if (wasFound != null) {
			System.out.println("QuizQuestion already exists in this quiz.");
			return wasFound;
		}
		
		quizQuestion = new QuizQuestion();
        quizQuestion.setQuestion(question);
        foundQuiz.getQuizQuestions().add(quizQuestion);
        quizQuestion.setQuiz(foundQuiz);
        quizQuestion=this.create(quizQuestion);
		
		return quizQuestion;
	}
	
	@Override
	public QuizQuestion removeQuestionFromQuiz(String Username, int quizId, int questionId) {
		
		QuizQuestion quizQuestion = null;
		
		quizQuestion = quizQuestionRepo.getByQuizIdAndQuestionId(quizId, questionId);
		
		if (quizQuestion == null) {
			return null;
		}
		
		quizQuestionRepo.delete(quizQuestion);
		
		return quizQuestion; // TODO: could be better to return the quiz with the question removed
	}


}
