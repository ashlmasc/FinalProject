import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Question } from '../../models/question';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from '../../services/question.service';
import { QuestionComponent } from '../question/question.component';

@Component({
  selector: 'app-questions',
  standalone: true,
  imports: [CommonModule, FormsModule, QuestionComponent],
  templateUrl: './questions.component.html',
  styleUrl: './questions.component.css',
})
export class QuestionsComponent implements OnInit {
  questions: Question[] = [];
  selected: Question | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private questionService: QuestionService
  ) {}

  ngOnInit(): void {
    this.getAllQuestions();
  }

  getAllQuestions() {
    this.questionService.index().subscribe({
      next: (questions: Question[]) => {
        this.questions = questions;
        console.log(this.questions);
      },
      error: (err: Error) => {
        console.error('Observer got an error: ' + err);
        this.questions = []; // Handle empty state
      },
    });
  }

  addNewQuestion(): void {
    this.router.navigateByUrl('/questions/new');
  }

  showQuestionDetail(question: Question): void {
    this.router.navigateByUrl(`/questions/${question.id}/edit`);
  }

  deleteQuestion(id: number): void {
    this.questionService.delete(id).subscribe({
      next: () => this.getAllQuestions(),
      error: (err) => console.error('Error deleting question:', err),
    });
  }

  selectQuestion(question: Question) {
    this.selected = question;
  }

  deselectQuestion() {
    this.selected = null;
  }

  editQuestion(question: Question) {
    // alert("clicked edit");
    this.router.navigateByUrl(`/modify/${question.id}`);
  }

  //old code
  // ngOnInit(): void {
  //   this.getAllQuestions();
  // }

  // getAllQuestions() {
  //   this.questionService.index().subscribe({
  //     next: (questions: Question[]) => {
  //       this.questions = questions;
  //       console.log(this.questions);
  //     },
  //     error: (err: Error) => console.error('Observer got an error: ' + err),
  //   });
  // }

  // showQuestionDetail(question: Question){
  //   this.router.navigateByUrl(`question/${question.id}`);
  // }

  // addNewQuestion(): void {
  //   this.router.navigateByUrl('/question/new');
  // }

  // editQuestion(question: Question): void {
  //   this.router.navigateByUrl(`/question/${question.id}`);
  // }

  // deleteQuestion(id: number): void {
  //   this.questionService.delete(id).subscribe({
  //     next: () => this.getAllQuestions(),
  //     error: (err) => console.error('Error deleting question:', err)
  //   });
  // }
}
