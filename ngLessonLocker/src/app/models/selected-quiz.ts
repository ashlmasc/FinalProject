import { QuizQuestion } from './quiz-question';

export class SelectedQuiz {
  id: number;
  title: string | null;
  quizQuestions: QuizQuestion[] = [];

  constructor(
    id: number = 0,
    title: string | null = null,
    quizQuestions: QuizQuestion[] = []
  ) {
    this.id = id;
    this.title = title;
    this.quizQuestions = quizQuestions;
  }
}
