package com.skilldistillery.lessonlocker.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.QuizQuestion;

public interface QuizQuestionRepository  extends JpaRepository<QuizQuestion, Integer>{
	


}
