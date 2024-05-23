package com.skilldistillery.lessonlocker.entities;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class QuizTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private Quiz quiz = null;

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
		quiz = em.find(Quiz.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		quiz = null;
	}
	
	@Test
	void test_quiz_entity_mapping() {
		assertNotNull(quiz);
	}

}
