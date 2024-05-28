import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { InstructorService } from '../../services/instructor.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Quiz } from '../../models/quiz';
import { QuizQuestion } from '../../models/quiz-question';
import { User } from '../../models/user';
import { QuizAnswer } from '../../models/quiz-answer';
import { Question } from '../../models/question';

@Component({
  selector: 'app-instructor-break',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './instructor-break.component.html',
  styleUrl: './instructor-break.component.css',
})
export class InstructorBreakComponent implements OnInit, OnDestroy {
  selectedQuizId: number = 0;

  selectedQuiz: Quiz | null = null;

  quizQuestion: QuizQuestion | null = null;

  quizQuestions: QuizQuestion[] = [];

  isFound: boolean = false;

  loggedInUserRole: string = '';

  loggedInUser: User | null = null;

  interval: any | null = null;

  clockElement: any;

  showAnswers: boolean = false;

  selectedAnswer: string = '0';

  showHints: boolean = false;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private instructorService: InstructorService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }

  postSubmittedAnswer(
    quizId: number,
    questionId: number,
    choiceId: number
  ): void {
    this.instructorService
      .submitQuestionAnswer(quizId, questionId, choiceId)
      .subscribe({
        next: (quizAnswer: QuizAnswer) => {
          alert('Completed submit:\n\n' + JSON.stringify(quizAnswer));
          console.log(quizAnswer);
          if (quizAnswer) {
            console.log(quizAnswer);
            alert(JSON.stringify(quizAnswer));
            return;
          } else {
            alert('submitQuestionAnswer quizAnswer result not found');
            return; // this.router.navigateByUrl('/');
          }
        },
        error: (err) => {
          alert(
            'submitQuestionAnswer Error: ' + err?.error?.message ||
              err?.message ||
              err
          );
          console.log(err?.error?.message || err?.message || err);
          return; // this.router.navigateByUrl('/login');
        },
      });
  }

  submitAnswer(quizQuestion: QuizQuestion): void {
    if (this.selectedAnswer === '0') {
      alert(' Please select an answer before submitting.');
      return;
    }

    console.log(
      this.selectedQuizId,
      quizQuestion.id,
      parseInt(this.selectedAnswer)
    );

    this.postSubmittedAnswer(
      this.selectedQuizId,
      quizQuestion.id,
      parseInt(this.selectedAnswer)
    );

    for (let i = 0; i < this.quizQuestions.length; i++) {
      let quizQuestion = this.quizQuestions[i].question;
      for (let j = 0; j < (quizQuestion?.choices?.length || 0); j++) {
        let choice = quizQuestion?.choices[j];
        if (choice?.id === parseInt(this.selectedAnswer)) {
          console.log(choice);
          console.log(quizQuestion);
          console.log(this.loggedInUser);
          if (choice?.correct === true) {
            alert('Correct answer');
          } else {
            alert('Incorrect answer');
          }
          return;
        }
      }
    }
  }

  startClock(): void {
    if (this.clockElement) {
      this.clockElement.innerHTML = new Date().toLocaleTimeString();
    }
    this.interval = setInterval(() => {
      if (this.clockElement) {
        this.clockElement.innerHTML = new Date().toLocaleTimeString();
      }
    }, 1000);
  }

  stopClock(): void {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  showTheAnswers(trueOrFalse: boolean): void {
    this.showAnswers = trueOrFalse;
  }

  showTheHints(): void {
    this.showHints = true;
  }

  hideTheHints(): void {
    this.showHints = false;
  }

  checkLoggedInUserRole(): string {
    let storedUser = this.authService.getLoggedInUserFromLocalStorage();
    if (storedUser) {
      this.loggedInUser = storedUser;
      return this?.loggedInUser?.role || '';
    } else {
      this.loggedInUser = null;
      return '';
    }
  }

  ngOnInit(): void {
    this.clockElement = document.getElementById('clock');

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
                  return;
                } else {
                  alert('Quiz not found');
                  return this.router.navigateByUrl('/');
                }
              },
              error: (err) => {
                alert('You must be logged in to user this page.');
                console.log(err?.error?.message || err?.message || err);
                return this.router.navigateByUrl('/login');
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
