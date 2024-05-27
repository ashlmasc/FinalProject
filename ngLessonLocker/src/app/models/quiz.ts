export class Quiz {
  id: number;
  title: string | null;
  enabled: boolean | null;
  createdAt: Date | null;
  updatedAt: Date | null;
  instructorUserId: number;
  quizQuestions: any;

  constructor(
    id: number = 0,
    title: string | null = null,
    enabled: boolean | null = null,
    createdAt: Date | null = null,
    updatedAt: Date | null = null,
    instructorUserId: number = 0,
    quizQuestions: any = []
  ) {
    this.id = id;
    this.title = title;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.instructorUserId = instructorUserId;
    this.quizQuestions = quizQuestions;
  }
}
