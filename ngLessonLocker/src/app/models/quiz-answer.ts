import { Choice } from './choice';
import { User } from './user';

class QuizAnswerId {
  userId: number;
  quizQuestionId: number;

  constructor(userId: number = 0, quizQuestionId: number = 0) {
    this.userId = userId;
    this.quizQuestionId = quizQuestionId;
  }
}

export class QuizAnswer {
  // userId: number;
  id: QuizAnswerId | null = null;
  quizQuestionId: number;
  createdAt: Date | null;
  choiceId: number;
  // user: User;
  choice: Choice;

  constructor(
    // userId: number = 0,
    id: QuizAnswerId,
    quizQuestionId: number = 0,
    createdAt: Date | null = null,
    choiceId: number = 0,
    // user: User,
    choice: Choice
  ) {
    // this.userId = userId;
    this.id = id;
    this.quizQuestionId = quizQuestionId;
    this.createdAt = createdAt;
    this.choiceId = choiceId;
    // this.user = user;
    this.choice = choice;
  }
}
