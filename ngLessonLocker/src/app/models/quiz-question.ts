export class QuizQuestion {
  id: number;
  quizId: number;
  questionId: number;

  constructor(id: number = 0, quizId: number = 0, questionId: number = 0) {
    this.id = id;
    this.quizId = quizId;
    this.questionId = questionId;
  }
}
