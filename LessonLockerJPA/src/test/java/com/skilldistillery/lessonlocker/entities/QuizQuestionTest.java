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

class QuizQuestionTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private QuizQuestion quizQuestion = null;

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
		quizQuestion = em.find(QuizQuestion.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		quizQuestion = null;
	}
	
	@Test
	void test_quizQuestion_entity_mapping() {
		assertNull(quizQuestion);
	}

}
