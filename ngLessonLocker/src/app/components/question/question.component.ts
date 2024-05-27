import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Question } from '../../models/question';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { InstructorService } from '../../services/instructor.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-question',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './question.component.html',
  styleUrl: './question.component.css',
})
export class QuestionComponent implements OnInit {
  @Input() selectedQuestion: Question | null = null;

  isLoaded: boolean = false;

  ngOnInit(): void {
    console.log(this.selectedQuestion);
  }

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private instructorService: InstructorService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  getQuestions() {
    this.router.navigateByUrl('reviews');
  }

  enableQuestion() {
    if (this.selectedQuestion != null) {
      this.isLoaded = false;
      this.callEnableOrDisableQuestion(this.selectedQuestion.id, true);
    }
  }

  disableQuestion() {
    if (this.selectedQuestion != null) {
      this.isLoaded = false;
      this.callEnableOrDisableQuestion(this.selectedQuestion.id, false);
    }
  }

  callEnableOrDisableQuestion(id: number, isEnabled: boolean) {
    this.instructorService.enableOrDisableQuestion(id, isEnabled).subscribe({
      next: (question: Question) => {
        this.selectedQuestion = question;
        console.log(this.selectedQuestion);
        this.isLoaded = true;
        // need to ensure other components have are updated
        this.router.navigateByUrl('reviews');
      },
      error: () => {
        this.isLoaded = true;
        return (err: Error) => console.error('Observer got an error: ' + err);
      },
    });
  }

  editQuestion() {
    alert('TODO - Edit question');
  }
}
