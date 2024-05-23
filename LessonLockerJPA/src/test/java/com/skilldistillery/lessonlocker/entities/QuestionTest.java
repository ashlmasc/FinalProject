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

class QuestionTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private Question question = null;

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
		question = em.find(Question.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		question = null;
	}
	
	@Test
	void test_question_entity_mapping() {
		assertNull(question);
	}

}
