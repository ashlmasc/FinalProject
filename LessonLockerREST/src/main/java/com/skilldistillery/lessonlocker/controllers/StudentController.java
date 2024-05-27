package com.skilldistillery.lessonlocker.controllers;

import java.security.Principal;
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

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.services.AuthService;
import com.skilldistillery.lessonlocker.services.QuestionService;
import com.skilldistillery.lessonlocker.services.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({"*", "http://localhost/"})
@RequestMapping("api")
public class StudentController {
	
	private StudentService studentService;
	private AuthService authService;
	private QuestionService questionService;
	public StudentController(StudentService studentService, AuthService authService, QuestionService questionService) {
		super();
		this.studentService = studentService;
		this.authService = authService;
		this.questionService = questionService;
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
	public Question getQuestionById(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res, Principal principal) {
	    Question question = studentService.show(principal.getName(), id);
	    if (question == null) {
	        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    }
	    return question;
	}

	@PostMapping("questions")
	public Question createQuestion(@RequestBody Question question, HttpServletRequest req, HttpServletResponse res, Principal principal) {
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

	@PutMapping("questions/{id}")
	public Question updateQuestion(@PathVariable("id") int id, @RequestBody Question question, HttpServletRequest req, HttpServletResponse res, Principal principal) {
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
	public void deleteQuestion(@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res, Principal principal) {
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
