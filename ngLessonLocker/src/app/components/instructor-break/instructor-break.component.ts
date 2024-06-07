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
import { reduce } from 'rxjs';

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

  elapsedElement: any;

  startDateTime: Date | null = null;

  showAnswers: boolean = false;

  selectedAnswer: string = '0';

  showHints: boolean = false;

  quizAnswers: QuizAnswer[] = [];

  hasAnswered: boolean = false;

  reducedQuizAnswers: any = {};

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private instructorService: InstructorService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  // reduce quizAnswers to collection of sums of choices
  /*

  [
    {
        "id": {
            "userId": 1,
            "quizQuestionId": 1
        },
        "createdAt": null,
        "choice": {
            "id": 1,
            "content": "Choice",
            "position": 1,
            "correct": false,
            "explanation": "Explanation"
        }
    }
  ] 
  */

  getAnswerCount(choiceId: number): string {
    console.log(choiceId);
    console.log(this.reducedQuizAnswers);
    console.log(this.reducedQuizAnswers[choiceId]);
    let numericValue: number = this.reducedQuizAnswers[choiceId || 0];
    let stringValue: string = numericValue ? numericValue.toString() : '';
    if (stringValue === '') {
      return '';
    } else {
      return stringValue + ' votes ';
    }
  }

  reduceQuizAnswers(quizAnswers: QuizAnswer[]): any {
    let reducedAnswers: any = {};
    for (let i = 0; i < quizAnswers.length; i++) {
      let quizAnswer = quizAnswers[i];
      let choiceId = quizAnswer.choice?.id || 0;
      if (reducedAnswers[choiceId]) {
        reducedAnswers[choiceId] += 1;
      } else {
        reducedAnswers[choiceId] = 1;
      }
    }
    this.reducedQuizAnswers = reducedAnswers;
    return reducedAnswers;
  }

  getQuizAnswers(): void {
    this.instructorService.loadAllQuizAnswers().subscribe({
      next: (quizAnswers: QuizAnswer[]) => {
        console.log(quizAnswers);
        if (quizAnswers) {
          console.log(quizAnswers);
          this.quizAnswers = quizAnswers;
          this.reduceQuizAnswers(this.quizAnswers);
          return;
        } else {
          console.log('Quiz Answers not found');
          // return this.router.navigateByUrl('/');
          return;
        }
      },
      error: (err) => {
        alert('You must be logged in to use this page.');
        console.log(err?.error?.message || err?.message || err);
        //return this.router.navigateByUrl('/login');
        return;
      },
    });
  }

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
          console.log('Completed submit:\n\n' + JSON.stringify(quizAnswer));
          console.log(quizAnswer);
          if (quizAnswer) {
            console.log(quizAnswer);
            console.log(JSON.stringify(quizAnswer));
            this.getQuizAnswers();
            return;
          } else {
            console.log('submitQuestionAnswer quizAnswer result not found');
            return; // this.router.navigateByUrl('/');
          }
        },
        error: (err) => {
          console.log(
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
      alert('Please select an answer before submitting.');
      return;
    }

    console.log(
      this.selectedQuizId,
      quizQuestion.question?.id,
      parseInt(this.selectedAnswer)
    );

    this.postSubmittedAnswer(
      this.selectedQuizId,
      quizQuestion.question?.id || 0,
      parseInt(this.selectedAnswer)
    );

    this.hasAnswered = true;

    for (let i = 0; i < this.quizQuestions.length; i++) {
      let quizQuestion = this.quizQuestions[i].question;
      for (let j = 0; j < (quizQuestion?.choices?.length || 0); j++) {
        let choice = quizQuestion?.choices[j];
        if (choice?.id === parseInt(this.selectedAnswer)) {
          console.log(choice);
          console.log(quizQuestion);
          console.log(this.loggedInUser);
          if (choice?.correct === true) {
            console.log('Correct answer');
          } else {
            console.log('Incorrect answer');
          }
          return; // Don't forget this is an early return!
        }
      }
    }

    // Don't forget the early return above!
  }

  // convertDateTime(dateTime: string): string {
  //   if (!dateTime) {
  //     return '';
  //   }
  //   let date = new Date(dateTime);
  //   return date.toLocaleString();
  // }

  checkSelectedQuiz(): string | null {
    return localStorage.getItem('selectedQuizId');
  }

  storeSelectedQuizIdInLocalStorage(quizId: number): void {
    console.log('selected quiz id: ' + quizId);
    localStorage.setItem('selectedQuizId', quizId.toString());
    localStorage.setItem('selectedQuiz', JSON.stringify(this.selectedQuiz));
    this.router.navigateByUrl('/reviews');
  }

  removeSelectedQuizIdFromLocalStorage(): void {
    console.log('removeSelectedQuizIdFromLocalStorage');
    localStorage.removeItem('selectedQuizId');
    localStorage.removeItem('selectedQuiz');
  }

  startClock(): void {
    if (this.clockElement) {
      this.clockElement.innerHTML = new Date().toLocaleTimeString();
      this.startDateTime = new Date();
    }
    this.interval = setInterval(() => {
      if (this.clockElement) {
        this.clockElement.innerHTML = new Date().toLocaleTimeString();
        // time difference in seconds between now and this.startDatetime
        if (this.startDateTime) {
          let timeDiff = Math.floor(
            (new Date().getTime() - this.startDateTime?.getTime()) / 1000
          );
          if (this.elapsedElement) {
            if (timeDiff % 15 === 0) {
              console.log('***   Getting Quiz Answers   ***');
              this.getQuizAnswers();
            }
            this.elapsedElement.innerHTML =
              timeDiff +
              ' seconds' +
              ' = ' +
              Math.floor(timeDiff / 60) +
              ' minutes ' +
              (timeDiff % 60) +
              ' seconds';
          }
        }
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
    this.elapsedElement = document.getElementById('elapsed');

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
                alert('You must be logged in to use this page.');
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
