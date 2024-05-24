package com.skilldistillery.lessonlocker.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Quiz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private Boolean enabled;

	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@CreationTimestamp
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@JoinColumn(name = "instructor_user_id")
	@JsonIgnore
	@ManyToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "quiz")
	List<QuizQuestion> quizQuestions;
	
	public Quiz() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	// NOTE: These are use for list mapping in the 'Quiz' class

//	public void addQuizQuestion(QuizQuestion quizquestion) {
//	    if (quizQuestions == null) {
//	       quizQuestions = new ArrayList<>();
//	    }
//	    if (!quizQuestions.contains(quizquestion)) {
//	        quizQuestions.add(quizquestion);
//	        if (quizquestion.getQuiz() != null && !quizquestion.getQuiz().equals(this)) {
//	            quizquestion.getQuiz().removeQuizQuestion(quizquestion);
//	        }
//	        quizquestion.setQuiz(this);
//	    }
//	}
	
//	public void removeQuizQuestion(QuizQuestion quizquestion) {
//	    if (quizQuestions != null && quizQuestions.contains(quizquestion)) {
//	        quizQuestions.remove(quizquestion);
//	        quizquestion.setQuiz(null);
//	    }
//	}

	public List<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}

	public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quiz other = (Quiz) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", enabled=" + enabled + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
}
