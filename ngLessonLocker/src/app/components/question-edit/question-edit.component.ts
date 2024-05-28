import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { QuestionService } from '../../services/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from '../../models/question';
import { Choice } from '../../models/choice';

@Component({
  selector: 'app-question-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './question-edit.component.html',
  styleUrl: './question-edit.component.css',
})
export class QuestionEditComponent implements OnInit {
  selected: Question | null = null;
  newChoice: Choice = new Choice();


  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const questionIdStr = params.get("id");
      if (questionIdStr) {
        const questionId = parseInt(questionIdStr);
        if (!isNaN(questionId)) {
          this.questionService.show(questionId).subscribe({
            next: (question) => this.selected = question,
            error: () => this.router.navigateByUrl('/not-found')
          });
        } else {
          this.router.navigateByUrl('/not-found');
        }
      } else {
        this.selected = new Question(); // Prepare for creating a new question
      }
    });
  }

  saveQuestion(): void {
    if (this.selected && this.selected.id) {
      this.selected.enabled = false;
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
    if (this.selected) {
      this.selected.choices.push({ ...this.newChoice });
      this.newChoice = new Choice();
    }
  }

  removeChoice(index: number): void {
    if (this.selected) {
      this.selected.choices.splice(index, 1);
    }
  }

  deleteQuestion(): void {
    if (this.selected && this.selected.id) {
      this.questionService.delete(this.selected.id).subscribe({
        next: () => this.router.navigateByUrl('/questions'),
        error: (err) => console.error('Error deleting question:', err)
      });
    }
  }
}