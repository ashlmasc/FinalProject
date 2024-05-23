package com.skilldistillery.lessonlocker.entities;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class QuizAnswerTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private QuizAnswer quizAnswer = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("LessonLockerJPA");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		QuizAnswerId pid = new QuizAnswerId();
		pid.setQuizQuestionId(2);
		pid.setUserId(1);
		quizAnswer = em.find(QuizAnswer.class, pid);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		quizAnswer = null;
	}
	
	@Test
	void test_quizQuestion_entity_mapping() {
		assertNull(quizAnswer);
	}
	


}
