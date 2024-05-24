package com.skilldistillery.lessonlocker.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.services.AuthService;
import com.skilldistillery.lessonlocker.services.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({"*", "http://localhost/"})
@RequestMapping("api")
public class StudentController {
	
	private StudentService studentService;
	private AuthService authService;
	
	public StudentController(StudentService studentService, AuthService authService) {
		super();
		this.studentService = studentService;
		this.authService = authService;
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
	public Question show(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") int id, Principal principal) {
		return studentService.show(principal.getName(), id);
	}

}
