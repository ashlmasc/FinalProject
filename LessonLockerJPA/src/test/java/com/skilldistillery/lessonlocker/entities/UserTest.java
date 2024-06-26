package com.skilldistillery.lessonlocker.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UserTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private User user = null;

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
		user = em.find(User.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user = null;
	}
	
	@Test
	void test_user_entity_mapping() {
		assertNotNull(user);
		assertEquals(1, user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getCohort());
		System.out.println(user.getEnabled());
		System.out.println(user.getCreatedAt());
		System.out.println(user.getRole());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getUpdatedAt());
	}
	
	@Test
	void test_user_has_question_mapping() {
		assertNotNull(user.getQuestions().size());
	}
	
	@Test
	void test_user_has_quizzes_mapping() {
		assertNotNull(user.getQuizzes().size());
	}
	
	@Test
	void test_user_has_quizAnswers_mapping() {
		assertNotNull(user.getQuizAnswers().size());
	}

}
