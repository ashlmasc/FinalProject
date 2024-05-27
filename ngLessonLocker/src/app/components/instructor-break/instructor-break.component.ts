import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { InstructorService } from '../../services/instructor.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Quiz } from '../../models/quiz';
import { QuizQuestion } from '../../models/quiz-question';

@Component({
  selector: 'app-instructor-break',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './instructor-break.component.html',
  styleUrl: './instructor-break.component.css',
})
export class InstructorBreakComponent implements OnInit {
  selectedQuizId: number = 0;

  selectedQuiz: Quiz | null = null;

  quizQuestion: QuizQuestion | null = null;

  quizQuestions: QuizQuestion[] = [];

  isFound: boolean = false;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private instructorService: InstructorService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        let quizIdStr = params.get('id');
        console.log(quizIdStr);
        if (quizIdStr) {
          let quizId = parseInt(quizIdStr);
          if (isNaN(quizId)) {
            console.log(quizId);
            this.router.navigateByUrl('/not-found');
          } else {
            this.selectedQuizId = quizId;
            this.instructorService.loadQuiz(quizId).subscribe({
              next: (quiz: Quiz) => {
                console.log(quiz);
                if (quiz) {
                  this.selectedQuiz = quiz;
                  this.quizQuestions = quiz.quizQuestions;
                  console.log(this.selectedQuiz);
                  this.isFound = true;
                } else {
                  alert('Quiz not found');
                  //return this.router.navigateByUrl('/not-found');
                  return;
                }
              },
              error: (err) => {
                alert(err?.error?.message || err?.message || err);
                //return this.router.navigateByUrl('/not-found');
                return;
              },
            });
          }
        }
      },
    });
  }

  loadNewQuiz(quizId: number) {
    this.instructorService.loadQuiz(quizId).subscribe({
      next: (quiz: Quiz) => {
        console.log(quiz);
        if (quiz) {
          alert(JSON.stringify(quiz));
          console.log(quiz);
          this.router.navigateByUrl('/quizzes/' + quiz.id);
        } else {
          alert('Quiz not found');
          //return this.router.navigateByUrl('/not-found');
          return;
        }
      },
      error: (err) => {
        alert(err?.error?.message || err?.message || err);
        //return this.router.navigateByUrl('/not-found');
        return;
      },
    });
  }
}
