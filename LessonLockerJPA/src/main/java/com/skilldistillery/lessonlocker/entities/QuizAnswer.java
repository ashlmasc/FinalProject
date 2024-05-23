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
	
	@ManyToOne
	@JoinColumn(name = "choice_id")
	private Choice choice;
	
	public QuizAnswer() {
		super();
	}
	
	public QuizAnswerId getId() {
		return id;
	}

	public void setId(QuizAnswerId id) {
		this.id = id;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public QuizQuestion getQuizQuestion() {
		return quizQuestion;
	}



	public void setQuizQuestion(QuizQuestion quizQuestion) {
		this.quizQuestion = quizQuestion;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Choice getChoice() {
		return choice;
	}



	public void setChoice(Choice choice) {
		this.choice = choice;
	}



	@Override
	public String toString() {
		return "QuizAnswer [createdAt=" + createdAt + "]";
	}

	
}
