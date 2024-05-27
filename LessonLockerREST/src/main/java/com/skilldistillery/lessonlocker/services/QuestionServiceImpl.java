package com.skilldistillery.lessonlocker.services;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.repositories.QuestionRepository;


@Service
public class QuestionServiceImpl implements QuestionService {
	
	private QuestionRepository questionRepo;

	
	public QuestionServiceImpl(QuestionRepository questionRepo) {
		super();
		this.questionRepo = questionRepo;
	}

	@Override
	public Question getQuestionById(int id) {
		return questionRepo.findById(id).orElse(null);
	}

    @Override
    public Question createQuestion(Question question) {
        if (question.getChoices() != null) {
            question.getChoices().forEach(choice -> choice.setQuestion(question));
        }
        if (question.getTags() != null) {
            question.getTags().forEach(tag -> tag.getQuestions().add(question));
        }
        return questionRepo.save(question);
    }

    @Override
    public Question updateQuestion(int id, Question question) {
        Question existingQuestion = getQuestionById(id);
        if (existingQuestion != null) {
            existingQuestion.setQuestion(question.getQuestion());
            existingQuestion.setHint(question.getHint());
            existingQuestion.setExplanation(question.getExplanation());
            existingQuestion.setEnabled(question.getEnabled());

            // Update choices
            if (question.getChoices() != null) {
                existingQuestion.getChoices().clear();
                question.getChoices().forEach(choice -> {
                    choice.setQuestion(existingQuestion);
                    existingQuestion.getChoices().add(choice);
                });
            }

            // Update tags
            if (question.getTags() != null) {
                existingQuestion.getTags().clear();
                question.getTags().forEach(tag -> {
                    tag.getQuestions().add(existingQuestion);
                    existingQuestion.getTags().add(tag);
                });
            }

            return questionRepo.save(existingQuestion);
        }
        return null;
    }

	@Override
	public boolean deleteQuestion(int id) {
		if (questionRepo.existsById(id)) {
            questionRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
