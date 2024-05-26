package com.skilldistillery.lessonlocker.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizAnswer;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.repositories.QuestionRepository;
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
	private AuthService authService;
	private QuestionRepository questionRepo;

	public QuizController(QuizAnswerService quizAnswerService, QuizService quizService,
			QuizQuestionService quizQuestionService, AuthService authService, QuestionRepository questionRepo) {
		super();
		this.questionRepo = questionRepo;
		this.quizService = quizService;
		this.quizQuestionService = quizQuestionService;
		this.quizAnswerService = quizAnswerService;
		this.authService = authService;
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

	// @RequestParam(value = "city", required = false) String searchByCityName,
	// @PathVariable("cityId") int cityId,
	// @RequestBody City city,

	@PostMapping("quizzes")
	public Quiz createQuiz(@RequestBody Map<String, String> payload, HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		
		String title = payload.get("title");
		
		Integer questionId = Integer.parseInt(payload.get("questionId"));

		Quiz newQuiz = null;

		User loggedInUser = authService.getUserByUsername(principal.getName());

		if (loggedInUser == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		
		System.out.println(title);

		// title
		// enabled
		// instructor_user_id

		try {

			newQuiz = new Quiz();
			newQuiz.setTitle(title);
			newQuiz.setEnabled(true);
			newQuiz.setUser(loggedInUser);
			newQuiz = quizService.create(newQuiz);
			
			if (newQuiz == null) {
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			
			
			QuizQuestion quizQuestion = new QuizQuestion();
			
			quizQuestion.setQuiz(newQuiz);
			
			Optional<Question> optSelectedQuestion = questionRepo.findById(questionId);
			
			if (optSelectedQuestion.isPresent()) {
                Question question = optSelectedQuestion.get();
                quizQuestion.setQuestion(question);
                
                quizQuestionService.create(quizQuestion);
            }

	

			res.setStatus(HttpServletResponse.SC_CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
		}

		return newQuiz;
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

	@GetMapping("quiz-answers/{quizId}")
	public List<QuizAnswer> getAllQuizAnswersByUserUsernameAndQuizId(@PathVariable("quizId") int quizId,
			HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<QuizAnswer> answers = null;
		List<QuizAnswer> filteredAnswers = new ArrayList<>();
		try {
			answers = quizAnswerService.getAllQuizAnswersByUserUsername(principal.getName());
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		for (QuizAnswer quizAnswer : answers) {
			if (quizAnswer.getQuizQuestion().getQuiz().getId() == quizId) {
				filteredAnswers.add(quizAnswer);
			}
		}
		return filteredAnswers;
	}

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
