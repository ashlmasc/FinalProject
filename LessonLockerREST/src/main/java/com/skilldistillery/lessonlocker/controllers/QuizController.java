package com.skilldistillery.lessonlocker.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizAnswer;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.services.AuthService;
import com.skilldistillery.lessonlocker.services.QuizAnswerService;
import com.skilldistillery.lessonlocker.services.QuizQuestionService;
import com.skilldistillery.lessonlocker.services.QuizService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class QuizController {

	private QuizService quizService;
	private QuizAnswerService quizAnswerService;
	private QuizQuestionService quizQuestionService;

	public QuizController(QuizAnswerService quizAnswerService, QuizService quizService,
			QuizQuestionService quizQuestionService) {
		super();
		this.quizService = quizService;
		this.quizQuestionService = quizQuestionService;
		this.quizAnswerService = quizAnswerService;
	}

	@GetMapping("quizzes")
	public List<Quiz> getAllQuizByUserUsername(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<Quiz> quizzes = null;
		try {
			quizzes = quizService.getAllQuizByUserUsername(principal.getName());
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return quizzes;
	}

	@GetMapping("quiz-answers")
	public List<QuizAnswer> getAllQuizAnswersByUserUsername(HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		List<QuizAnswer> answers = null;
		try {
			answers = quizAnswerService.getAllQuizAnswersByUserUsername(principal.getName());
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return answers;
	}

	// TODO: Implement this method
	@GetMapping("quiz-answers/{quizId}")
	public List<QuizAnswer> getAllQuizAnswersByUserUsernameAndQuizId( @PathVariable("quizId") int quizId,HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<QuizAnswer> answers = null;
		try {
			// answers = quizAnswerService.getAllQuizAnswersByUserUsernameAndQuizId(principal.getName(), quizId);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return answers;
	}

	// TODO: Implement this method
	@GetMapping("quizzes/{id}/questions")
	public List<QuizQuestion> getAllQuestionsByQuizId(@PathVariable("id") int id, HttpServletRequest req,
			HttpServletResponse res, Principal principal) {
		List<QuizQuestion> questions = null;
		try {
			questions = quizQuestionService.questionsForQuizId(principal.getName(), id);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return questions;
	}

}
