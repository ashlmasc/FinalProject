export class QuizAnswer {
  userId: number;
  quizQuestionId: number;
  createdAt: Date | null;
  choiceId: number;

  constructor(
    userId: number = 0,
    quizQuestionId: number = 0,
    createdAt: Date | null = null,
    choiceId: number = 0
  ) {
    this.userId = userId;
    this.quizQuestionId = quizQuestionId;
    this.createdAt = createdAt;
    this.choiceId = choiceId;
  }
}
