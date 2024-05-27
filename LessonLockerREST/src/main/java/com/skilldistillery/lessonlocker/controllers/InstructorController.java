package com.skilldistillery.lessonlocker.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.services.AuthService;
import com.skilldistillery.lessonlocker.services.InstructorService;
import com.skilldistillery.lessonlocker.services.QuestionService;
import com.skilldistillery.lessonlocker.services.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class InstructorController {

	private InstructorService instructorService;
	private QuestionService questionService;
	private AuthService authService;

	public InstructorController(InstructorService InstructorService, AuthService authService,
			QuestionService questionService) {
		super();
		this.instructorService = InstructorService;
		this.authService = authService;
		this.questionService = questionService;
	}

	@GetMapping("instructors/questions")
	public List<Question> findAllApprovedQuestions(HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		List<Question> allApprovedQuestions = null;
		try {
			allApprovedQuestions = questionService.getAllQuestionsByIsEnabled(true);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return allApprovedQuestions;
	}

	@GetMapping("students/questions")
	public List<Question> findAll(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<Question> allQuestions = null;
		try {
			allQuestions = instructorService.findAll();
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return allQuestions;
	}

	@GetMapping("students/questions/reviews/{id}")
	public Question findAll(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res,
			Principal principal) {
		Question question = null;
		try {
			question = questionService.findById(id);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return question;
	}

	@GetMapping("students/questions/username/{username}")
	public List<Question> findAllByUserUsername(@PathVariable("username") String username, HttpServletRequest req,
			HttpServletResponse res, Principal principal) {
		List<Question> allQuestionsByUsername = null;
		try {
			allQuestionsByUsername = instructorService.findAllByUserUsername(username);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return allQuestionsByUsername;
	}

	@GetMapping("students/questions/cohort/{cohort}")
	public List<Question> findAllByUserCohort(@PathVariable("cohort") String cohort, HttpServletRequest req,
			HttpServletResponse res, Principal principal) {
		List<Question> allQuestionsByCohort = null;
		try {
			allQuestionsByCohort = instructorService.findAllByUserCohort(cohort);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
		}
		return allQuestionsByCohort;
	}

}
