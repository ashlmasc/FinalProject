export class Choice {
  id: number;
  content: string | null;
  position: number;
  correct: boolean | null;
  explanation: string | null;
  questionId: number;

  constructor(
    id: number = 0,
    content: string | null = null,
    position: number = 0,
    correct: boolean | null = null,
    explanation: string | null = null,
    questionId: number = 0
  ) {
    this.id = id;
    this.content = content;
    this.position = position;
    this.correct = correct;
    this.explanation = explanation;
    this.questionId = questionId;
  }
}
