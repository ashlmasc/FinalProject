export class Question {
  id: number;
  question: string | null;
  createdAt: Date | null;
  updatedAt: Date | null;
  enabled: boolean;
  hint: string | null;
  explanation: string | null;
  userId: number;
  tags: any;
  user: any;

  constructor(
    id: number = 0,
    question: string | null = null,
    createdAt: Date | null = null,
    updatedAt: Date | null = null,
    enabled: boolean = true,
    hint: string | null = null,
    explanation: string | null = null,
    userId: number = 0,
    tags: any = [],
    user: any = null
  ) {
    this.id = id;
    this.question = question;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.enabled = enabled;
    this.hint = hint;
    this.explanation = explanation;
    this.userId = userId;
    this.tags = tags;
    this.user = user;
  }
}
