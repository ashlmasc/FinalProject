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

class ChoiceTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private Choice choice = null;

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
		choice = em.find(Choice.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		choice = null;
	}
	
	@Test
	void test_choice_entity_mapping() {
		assertNotNull(choice);
		System.out.println(choice.getId());
		assertEquals(1, choice.getId());
		System.out.println(choice.getContent());
		assertEquals("Choice", choice.getContent());
		System.out.println(choice.getPosition());
		assertEquals(1, choice.getPosition());
		System.out.println(choice.isCorrect());
		assertEquals(true, choice.isCorrect());
		System.out.println(choice.getExplanation());
		assertEquals("Explanation", choice.getExplanation());
		System.out.println(choice.getQuestion().getId());
		assertEquals(1, choice.getQuestion().getId());
	}

}
