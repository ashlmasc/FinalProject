package com.skilldistillery.lessonlocker.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="cohort")
	private String cohort;

	@Column(name="enabled")
	private Boolean enabled;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="role")	
	private String role;
	
	@OneToMany(mappedBy="user")
	List<Question> questions;
	
	@OneToMany(mappedBy="user")
	List<Quiz> quizzes;
	
	@OneToMany(mappedBy = "user")
	private List<QuizAnswer> quizAnswers;
	
	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCohort() {
		return cohort;
	}

	public void setCohort(String cohort) {
		this.cohort = cohort;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public void addQuestion(Question question) {
	    if (questions == null) {
	       questions = new ArrayList<>();
	    }
	    if (!questions.contains(question)) {
	        questions.add(question);
	        if (question.getUser() != null && !question.getUser().equals(this)) {
	            question.getUser().removeQuestion(question);
	        }
	        question.setUser(this);
	    }
	}
	public void removeQuestion(Question question) {
	    if (questions != null && questions.contains(question)) {
	        questions.remove(question);
	        question.setUser(null);
	    }
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public List<QuizAnswer> getQuizAnswers() {
		return quizAnswers;
	}

	public void setQuizAnswers(List<QuizAnswer> quizAnswers) {
		this.quizAnswers = quizAnswers;
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
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", cohort=" + cohort
				+ ", enabled=" + enabled + ", firstName=" + firstName + ", lastName=" + lastName + ", createdAt="
				+ createdAt + ", role=" + role + "]";
	}
	
	

}
