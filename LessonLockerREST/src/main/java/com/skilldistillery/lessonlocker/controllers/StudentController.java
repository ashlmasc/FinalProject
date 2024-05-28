package com.skilldistillery.lessonlocker.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.lessonlocker.entities.Choice;
import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.Quiz;
import com.skilldistillery.lessonlocker.entities.QuizAnswer;
import com.skilldistillery.lessonlocker.entities.QuizAnswerId;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.services.AuthService;
import com.skilldistillery.lessonlocker.services.QuestionService;
import com.skilldistillery.lessonlocker.services.QuizAnswerService;
import com.skilldistillery.lessonlocker.services.QuizQuestionService;
import com.skilldistillery.lessonlocker.services.QuizService;
import com.skilldistillery.lessonlocker.services.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class StudentController {

	private StudentService studentService;
	private AuthService authService;
	private QuestionService questionService;
	private QuizAnswerService quizAnswerService;
	private QuizQuestionService quizQuestionService;
	private QuizService quizService;

	public StudentController(StudentService studentService, AuthService authService, QuestionService questionService,
			QuizAnswerService quizAnswerService, QuizQuestionService quizQuestionService, QuizService quizService) {
		super();
		this.studentService = studentService;
		this.authService = authService;
		this.questionService = questionService;
		this.quizAnswerService = quizAnswerService;
		this.quizQuestionService = quizQuestionService;
		this.quizService = quizService;
	}

	@PostMapping("quiz-answers/{quizId}/{questionId}/{choiceId}")
	public QuizAnswer createNewQuizAnswer(@PathVariable("quizId") int quizId,
			@PathVariable("questionId") int questionId, @PathVariable("choiceId") int choiceId, HttpServletRequest req,
			HttpServletResponse res, Principal principal) {

		QuizAnswer newQuizAnswer = null;

		try {

			User user = authService.getUserByUsername(principal.getName());
			if (user == null) {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			
			// TODO: Move this business logic into service
			
			System.out.println("quizId: " + quizId);
			System.out.println("questionId: " + questionId);
			System.out.println("choiceId: " + choiceId);
			System.out.println("userId: " + user.getId());
			

			Quiz quiz = quizService.getById( quizId);

			//Question question = questionService.findById(questionId);
			
			QuizQuestion quizQuestion = quizQuestionService.getByQuizIdAndQuestionId(quizId, questionId);
			
			System.out.println(quizQuestion);
			
			if (quizQuestion == null) {
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			
			QuizAnswerId quizAnswerId = new QuizAnswerId(user.getId(),quizQuestion.getId());
	
			newQuizAnswer = new QuizAnswer();
			newQuizAnswer.setId(quizAnswerId);
			newQuizAnswer.setQuizQuestion(quizQuestion);
			
			Choice choice = new Choice();
			choice.setId(choiceId);
			newQuizAnswer.setChoice(choice);
			newQuizAnswer.setId(quizAnswerId);
			newQuizAnswer.setUser(user);
			LocalDateTime now = LocalDateTime.now();
			newQuizAnswer.setCreatedAt(now);

			newQuizAnswer=quizAnswerService.create(newQuizAnswer);
			
			System.out.println(newQuizAnswer);
			
			return newQuizAnswer;

		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("questions")
	public List<Question> getAllQuestionsForUser(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<Question> questions = null;
		try {
			questions = studentService.getAllQuestions(principal.getName());
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return questions;
	}

	@GetMapping("questions/{id}")
	public Question getQuestionById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		Question question = studentService.show(principal.getName(), id);
		if (question == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return question;
	}

	@PostMapping("questions")
	public Question createQuestion(@RequestBody Question question, HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		try {
			// Ensure user is set
			User user = authService.getUserByUsername(principal.getName());
			if (user == null) {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			question.setUser(user); // Set the user
			Question createdQuestion = questionService.createQuestion(question);
			res.setStatus(HttpServletResponse.SC_CREATED);
			return createdQuestion;
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
			return null;
		}
	}

	@PutMapping("modify/{id}")
	public Question updateQuestion(@PathVariable("id") int id, @RequestBody Question question, HttpServletRequest req,
			HttpServletResponse res, Principal principal) {
		try {
			// Ensure user is set
			User user = authService.getUserByUsername(principal.getName());
			if (user == null) {
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			question.setUser(user); // Set the user
			Question updatedQuestion = questionService.updateQuestion(id, question);
			if (updatedQuestion == null) {
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			return updatedQuestion;
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
			return null;
		}
	}

	@DeleteMapping("questions/{id}")
	public void deleteQuestion(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		try {
			if (questionService.deleteQuestion(id)) {
				res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
		}
	}
}
