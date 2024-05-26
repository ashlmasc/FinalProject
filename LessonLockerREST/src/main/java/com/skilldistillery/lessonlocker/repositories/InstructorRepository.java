package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.Question;

public interface InstructorRepository extends JpaRepository<Question, Integer> {
	
	List<Question> findAll();
	
	List<Question> findAllByUserUsername(String username);
	
	List<Question> findAllByUserCohort(String cohort);

}
