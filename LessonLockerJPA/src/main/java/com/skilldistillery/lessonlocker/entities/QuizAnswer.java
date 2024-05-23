package com.skilldistillery.lessonlocker.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "quiz_answer")
public class QuizAnswer {
	
	@EmbeddedId
	private QuizAnswerId id;
	
	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "quiz_question_id") // DB column name
	@MapsId(value = "quizQuestionId")     // Field in ID class
	private QuizQuestion quizQuestion;

	@ManyToOne
	@JoinColumn(name = "user_id") // DB column
	@MapsId(value = "userId")     // Field in ID class
	private User user;
	
	// fk choice_id
	// private Choice choice;
	
	public QuizAnswer() {
		super();
	}

	@Override
	public String toString() {
		return "QuizAnswer [createdAt=" + createdAt + "]";
	}

	
}
