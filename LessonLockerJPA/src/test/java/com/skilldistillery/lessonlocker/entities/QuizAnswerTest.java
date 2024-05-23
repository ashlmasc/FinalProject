package com.skilldistillery.lessonlocker.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
		pid.setQuizQuestionId(1);
		pid.setUserId(1);
		quizAnswer = em.find(QuizAnswer.class, pid);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		quizAnswer = null;
	}
	
	@Test
	void test_quizAnswer_basic_entity_mapping() {
		assertNotNull(quizAnswer);
		System.out.println(quizAnswer);
		System.out.println(quizAnswer.getCreatedAt());
		assertNull(quizAnswer.getCreatedAt());
	}
	
	@Test
	void test_quizAnswer_has_question_entity_mapping() {
		System.out.println(quizAnswer);
		System.out.println(quizAnswer.getQuizQuestion().getId());
		assertEquals(1,quizAnswer.getQuizQuestion().getId());
	}
	
	@Test
	void test_quizAnswer_has_has_user_entity_mapping() {
		System.out.println(quizAnswer);
		System.out.println(quizAnswer.getUser().getUsername());
		assertEquals("sheldon",quizAnswer.getUser().getUsername());
	}
	
	@Test
	void test_quizAnswer_has_choice_entity_mapping() {
		System.out.println(quizAnswer);
		System.out.println(quizAnswer.getChoice().getId());
		assertEquals(1,quizAnswer.getChoice().getId());
	}



}
