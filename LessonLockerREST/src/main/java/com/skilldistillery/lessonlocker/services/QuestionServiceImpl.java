package com.skilldistillery.lessonlocker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	QuestionRepository questionRepo;

	public QuestionServiceImpl(QuestionRepository questionRepo) {
		this.questionRepo = questionRepo;
	}

	@Override
	public List<Question> getAllQuestionsByIsEnabled(Boolean isEnabled) {
		List<Question> allQuestions = questionRepo.getAllQuestionsByEnabled(isEnabled);
		return allQuestions;
	}

	@Override
	public Question findById(int id) {
		return questionRepo.findById(id).get();
	}

	@Override
	public Question enableOrDisableQuestion(int id, boolean isEnabled) {

		Question foundQuestion = null;

		Optional<Question> questionOpt = questionRepo.findById(id);

		if (questionOpt.isPresent()) {
			foundQuestion = questionOpt.get();
			foundQuestion.setEnabled(isEnabled);
			foundQuestion = questionRepo.saveAndFlush(foundQuestion);
			return foundQuestion;
		}

		return foundQuestion;
	}

}
