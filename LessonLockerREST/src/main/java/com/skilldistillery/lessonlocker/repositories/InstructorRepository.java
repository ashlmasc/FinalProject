package com.skilldistillery.lessonlocker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.lessonlocker.entities.Question;
import com.skilldistillery.lessonlocker.entities.User;

public interface InstructorRepository extends JpaRepository<Question, Integer> {
	
	@Query("SELECT q FROM Question q ORDER BY q.updatedAt DESC")
	List<Question> findAll();
	
	List<Question> findAllByUserUsername(String username);
	
	List<Question> findAllByUserCohort(String cohort);
	
	@Query("SELECT u FROM User u WHERE u.cohort = :cohort")
	List<User> findAllUsersByCohort(@Param("cohort") String cohort);

}
