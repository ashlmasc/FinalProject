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

class TagTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private Tag tag = null;

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
		tag = em.find(Tag.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		tag = null;
	}
	
	@Test
	void test_tag_entity_mapping() {
		assertNotNull(tag);
	}

}
