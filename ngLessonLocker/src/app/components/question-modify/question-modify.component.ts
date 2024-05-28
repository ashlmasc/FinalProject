import { Component, OnInit } from '@angular/core';
import { Question } from '../../models/question';
import { Choice } from '../../models/choice';
import { QuestionService } from '../../services/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { QuestionComponent } from '../question/question.component';

@Component({
  selector: 'app-question-modify',
  standalone: true,
  imports: [FormsModule, CommonModule, QuestionComponent],
  templateUrl: './question-modify.component.html',
  styleUrl: './question-modify.component.css'
})
export class QuestionModifyComponent implements OnInit {
  selected: Question | null = null;
  newChoice: Choice = new Choice();
  correctChoice: Choice | null = null;
  isEditing = true;

  constructor(
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const questionIdStr = params.get('id');
      if (questionIdStr) {
        const questionId = parseInt(questionIdStr);
        if (!isNaN(questionId)) {
          this.questionService.show(questionId).subscribe({
            next: question => {
              this.selected = question;
              this.correctChoice = this.selected.choices.find(choice => choice.correct) || null;
              this.isEditing = false;
            },
            error: () => this.router.navigateByUrl('/not-found')
          });
        } else {
          this.router.navigateByUrl('/not-found');
        }
      } else {
        this.selected = new Question();
        this.isEditing = true;
      }
    });
  }

  saveQuestion(): void {
    if (this.selected) {
      if (this.correctChoice) {
        this.selected.choices.forEach(choice => choice.correct = false);
        this.correctChoice.correct = true;
      }
      if (this.selected.id) {
        this.questionService.update(this.selected.id, this.selected).subscribe({
          next: updatedQuestion => {
            this.selected = updatedQuestion;
            this.isEditing = false;
          },
          error: err => console.error('Error updating question:', err)
        });
      } else {
        this.questionService.create(this.selected).subscribe({
          next: createdQuestion => {
            this.selected = createdQuestion;
            this.isEditing = false;
          },
          error: err => console.error('Error creating question:', err)
        });
      }
    }
  }

  saveQuestion(): void {
    if (this.selected && this.selected.id) {
      this.questionService.update(this.selected.id, this.selected).subscribe({
        next: (updatedQuestion) => this.router.navigateByUrl(`/questions/${updatedQuestion.id}`),
        error: (err) => console.error('Error updating question:', err)
      });
    } else if (this.selected) {
      this.questionService.create(this.selected).subscribe({
        next: (createdQuestion) => this.router.navigateByUrl(`/questions/${createdQuestion.id}`),
        error: (err) => console.error('Error creating question:', err)
      });
    }
  }

  addChoice(): void {
    if (this.selected && this.newChoice.content) {
      this.selected.choices.push({ ...this.newChoice, correct: false });
      this.newChoice = new Choice();
    }
  }

  removeChoice(index: number): void {
    if (this.selected) {
      this.selected.choices.splice(index, 1);
    }
  }


  setCorrectChoice(choice: Choice): void {
    this.correctChoice = choice;
  }

  cancel(): void {
    this.router.navigateByUrl('/questions');
  }

  editQuestion(): void {
    this.isEditing = true;
  }

  backToQuestions(): void {
    this.router.navigateByUrl('/questions');
  }
}
