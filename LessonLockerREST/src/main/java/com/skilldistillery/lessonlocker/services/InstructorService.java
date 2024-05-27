package com.skilldistillery.lessonlocker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skilldistillery.lessonlocker.entities.Question;

@Service
public interface InstructorService {
	
	List<Question> findAll();
	
	List<Question> findAllByUserUsername(String username);
	
	List<Question> findAllByUserCohort(String cohort);

}
