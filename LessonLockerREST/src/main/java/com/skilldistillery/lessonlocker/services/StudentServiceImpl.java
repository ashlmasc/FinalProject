package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.repositories.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

	private QuestionRepository questionRepo;

	public StudentServiceImpl(QuestionRepository questionRepo) {
		super();
		this.questionRepo = questionRepo;
	}

	@Override
    public List<Question> getAllQuestions(String username) {
		List<Question> questions = questionRepo.getAllQuestionsByUserUsername(username);
		return questions;
    }

    @Override
    public Question show(String username, int id) {
        Question foundQuestion = questionRepo.getQuestionByUserUsernameAndId(username, id);
        if (foundQuestion != null) {
            return foundQuestion;
        } else {
            throw new EntityNotFoundException("Question not found for username: " + username + " and id: " + id);
        }
    }
}