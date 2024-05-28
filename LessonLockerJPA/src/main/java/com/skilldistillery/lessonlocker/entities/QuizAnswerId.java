package com.skilldistillery.lessonlocker.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

@Embeddable
public class QuizAnswerId implements Serializable {
	
		private static final long serialVersionUID = 1L;
		
		@Column(name = "user_id")
		private int userId;
		
		@Column(name = "quiz_question_id")
		private int quizQuestionId;

		public QuizAnswerId() {
			super();
		}
		
		public QuizAnswerId(int userId, int quizQuestionId) {
			super();
			this.userId = userId;
			this.quizQuestionId = quizQuestionId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getQuizQuestionId() {
			return quizQuestionId;
		}

		public void setQuizQuestionId(int quizQuestionId) {
			this.quizQuestionId = quizQuestionId;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
		public int hashCode() {
			return Objects.hash(quizQuestionId, userId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QuizAnswerId other = (QuizAnswerId) obj;
			return quizQuestionId == other.quizQuestionId && userId == other.userId;
		}

		@Override
		public String toString() {
			return "QuizAnswerId [userId=" + userId + ", quizQuestionId=" + quizQuestionId + "]";
		}
		
		
		
		

}
