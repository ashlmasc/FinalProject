package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.User;
import com.skilldistillery.lessonlocker.repositories.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {
	
	InstructorRepository instructorRepo;
	
	public InstructorServiceImpl(InstructorRepository instructorRepo) {
		super();
		this.instructorRepo = instructorRepo;
	}

	@Override
	public List<Question> findAll() {
		List<Question> allQuestions = instructorRepo.findAll();
		return allQuestions;
	}

	@Override
	public List<Question> findAllByUserUsername(String questionCreatorUsername) {
		List<Question> allQuestionsByUsername = instructorRepo.findAllByUserUsername(questionCreatorUsername);
		return allQuestionsByUsername;
	}

	@Override
	public List<Question> findAllByUserCohort(String questionCreatorCohort) {
		List<Question> allQuestionsByCohort = instructorRepo.findAllByUserCohort(questionCreatorCohort);
		return allQuestionsByCohort;
	}

	@Override
	public List<User> findAllUsersByUserCohort(String cohort) {
		List<User> allUsersByCohort = instructorRepo.findAllUsersByCohort(cohort);
		return allUsersByCohort;
	}

}
