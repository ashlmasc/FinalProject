package com.skilldistillery.lessonlocker.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String question;

	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	private Boolean enabled;
	
	private String hint;
	
	private String explanation;
	
	@ManyToMany
	@JoinTable(name="question_has_tag",
	joinColumns=@JoinColumn(name="question_id"),
	inverseJoinColumns=@JoinColumn(name="tag_id"))
	private List<Tag> tags;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnoreProperties({"questions", "quizzes"})
	private User user;
		
	@OneToMany(mappedBy = "question")
	List<Choice> choices;
	
	public Question() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", enabled=" + enabled + ", hint=" + hint + ", explanation=" + explanation + "]";
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	
	public void addChoice(Choice choice) {
	    if (choices == null) {
	       choices = new ArrayList<>();
	    }
	    if (!choices.contains(choice)) {
	        choices.add(choice);
	        if (choice.getQuestion() != null && !choice.getQuestion().equals(this)) {
	            choice.getQuestion().removeChoice(choice);
	        }
	        choice.setQuestion(this);
	    }
	}
	public void removeChoice(Choice choice) {
	    if (choices != null && choices.contains(choice)) {
	        choices.remove(choice);
	        choice.setQuestion(null);
	    }
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
		Question other = (Question) obj;
		return id == other.id;
	}


	
}
