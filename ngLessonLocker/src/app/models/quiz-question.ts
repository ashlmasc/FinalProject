import { Question } from './question';

export class QuizQuestion {
  id: number;
  quizId: number;
  questionId: number;
  question: Question | null;

  constructor(
    id: number = 0,
    quizId: number = 0,
    questionId: number = 0,
    question: Question = new Question()
  ) {
    this.id = id;
    this.quizId = quizId;
    this.questionId = questionId;
    this.question = question;
  }
}
