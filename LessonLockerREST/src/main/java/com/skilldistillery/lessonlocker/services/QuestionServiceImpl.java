package com.skilldistillery.lessonlocker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Choice;
import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.QuizQuestion;
import com.skilldistillery.lessonlocker.repositories.ChoiceRepository;
import com.skilldistillery.lessonlocker.repositories.QuestionRepository;
import com.skilldistillery.lessonlocker.repositories.QuizQuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	private QuestionRepository questionRepo;
	private ChoiceRepository choiceRepo;
	private QuizQuestionRepository quizQuestionRepo;

	public QuestionServiceImpl(QuestionRepository questionRepo, ChoiceRepository choiceRepo,
			QuizQuestionRepository quizQuestionRepo) {
		super();
		this.questionRepo = questionRepo;
		this.choiceRepo = choiceRepo;
		this.quizQuestionRepo = quizQuestionRepo;
	}

	@Override
	public Question getQuestionById(int id) {
		return questionRepo.findById(id).orElse(null);
	}

	@Override
	public Question createQuestion(Question question) {
		question.setEnabled(false);
		Question newQuestion = questionRepo.saveAndFlush(question);

		if (newQuestion.getChoices() != null) {
			// question.getChoices().forEach(choice -> choice.setQuestion(question));
			for (int i = 0; i < newQuestion.getChoices().size(); i++) {
				Choice choice = newQuestion.getChoices().get(i);
				choice.setQuestion(newQuestion);
				choiceRepo.saveAndFlush(choice);
			}

		}
//        if (question.getTags() != null) {
//            question.getTags().forEach(tag -> tag.Questions().add(question));
//        }
		return newQuestion;
	}

	@Override
	public Question updateQuestion(int id, Question question) {
		question.setEnabled(false);
		Question existingQuestion = getQuestionById(id);
		if (existingQuestion != null) {
			existingQuestion.setQuestion(question.getQuestion());
			existingQuestion.setHint(question.getHint());
			existingQuestion.setExplanation(question.getExplanation());
			existingQuestion.setEnabled(question.getEnabled());
			
			//delete all choices with question.getId
			
			// Update choices
			if (question.getChoices() != null) {
				existingQuestion.getChoices().clear();
				for (int i = 0; i < question.getChoices().size(); i++) {
					Choice choice = question.getChoices().get(i);
					choice.setQuestion(existingQuestion);
					choiceRepo.saveAndFlush(choice);
					existingQuestion.getChoices().add(choice);
				}
			}

			// Update tags
//            if (question.getTags() != null) {
//                existingQuestion.getTags().clear();
//                for (int i = 0; i < question.getTags().size(); i++) {
//                    Tag tag = question.getTags().get(i);
//                    tag.getQuestions().add(existingQuestion);
//                    existingQuestion.getTags().add(tag);
//                }
//            }

			return questionRepo.saveAndFlush(existingQuestion);
		}
		return null;
	}

	@Override
	public boolean deleteQuestion(int id) {
		if (questionRepo.existsById(id)) {
			Question question = questionRepo.findById(id).orElse(null);
			
			if(question != null) {
				question.setEnabled(false);
				questionRepo.saveAndFlush(question);
				return true;
			}
//			if (question != null) {
//				// Delete choices associated with the question
//				List<Choice> choices = question.getChoices();
//				for (Choice choice : choices) {
//					choiceRepo.delete(choice);
//				}
//				// Now delete the question
//				questionRepo.deleteById(id);
//				return true;
//			}
		}
		return false;
	}

	public List<Question> getAllQuestionsByIsEnabled(Boolean isEnabled) {
		List<Question> allQuestions = questionRepo.getAllQuestionsByEnabled(isEnabled);
		return allQuestions;
	}

	@Override
	public Question findById(int id) {
		Optional<Question> optQuestion = null;
		
		optQuestion = questionRepo.findById(id);
		
		if (optQuestion.isPresent()) {
			return optQuestion.get();
		} else {
			return null;
		}

		// return questionRepo.findById(id).get();
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

	@Override
	public QuizQuestion getByQuizIdAndQuestionId(int quizId, int questionId) {
		QuizQuestion foundQuizQuestion = null;
		foundQuizQuestion = quizQuestionRepo.getByQuizIdAndQuestionId(quizId, questionId);
		return foundQuizQuestion;
	}

}
