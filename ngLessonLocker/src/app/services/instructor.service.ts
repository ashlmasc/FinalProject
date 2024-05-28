import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Question } from '../models/question';
import { Observable, catchError, throwError } from 'rxjs';
import { Quiz } from '../models/quiz';
import { User } from '../models/user';
import { QuizAnswer } from '../models/quiz-answer';

@Injectable({
  providedIn: 'root',
})
export class InstructorService {
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) {}

  show(id: number): Observable<Question> {
    return this.http
      .get<Question>(
        this.url + 'api/students/questions/reviews/' + id,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('Service.index(): error retrieving : ' + err)
          );
        })
      );
  }

  // api/students/cohorts/c43
  // find all uses in cohort

  findAllUsersByCohort(cohort: string): Observable<User[]> {
    return this.http
      .get<User[]>(
        this.url + 'api/students/cohorts/' + cohort,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving .', err);
          return throwError(
            () => new Error('Service.index(): error retrieving . ' + err)
          );
        })
      );
  }

  loadQuiz(id: number): Observable<Quiz> {
    return this.http
      .get<Quiz>(this.url + 'api/quizzes/' + id, this.getHttpOptions())
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('Service.index(): error retrieving : ' + err)
          );
        })
      );
  }

  // http://localhost:8088/api/quizzes
  //        "title": "Instructor Quiz",
  //        "questionId": 1

  submitQuestionAnswer(
    quizId: number,
    questionId: number,
    choiceId: number
  ): Observable<QuizAnswer> {
    alert('inside submitQuestionAnswer in instructor service.');
    return this.http
      .post<QuizAnswer>(
        this.url + `api/quiz-answers/${quizId}/${questionId}/${choiceId}`,
        {
          quizId,
          questionId,
          choiceId,
        },
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('Service.index(): error retrieving : ' + err)
          );
        })
      );
  }

  createHostedQuestionQuiz(
    title: string,
    questionId: number
  ): Observable<Quiz> {
    return this.http
      .post<Quiz>(
        this.url + 'api/quizzes',
        {
          title: title,
          questionId: questionId,
        },
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('Service.index(): error retrieving : ' + err)
          );
        })
      );
  }

  enableOrDisableQuestion(
    id: number,
    isEnabled: boolean
  ): Observable<Question> {
    return this.http
      .post<Question>(
        this.url +
          'api/students/questions/reviews/' +
          id +
          `?enabled=${isEnabled}`,
        {},
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('Service.index(): error retrieving : ' + err)
          );
        })
      );
  }

  findAll(): Observable<Question[]> {
    return this.http
      .get<Question[]>(
        this.url + 'api/students/questions',
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('Service.index(): error retrieving : ' + err)
          );
        })
      );
  }

  findAllByUserUsername(username: String): Observable<Question[]> {
    return this.http
      .get<Question[]>(
        this.url + 'api/students/questions/username/' + username,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving .', err);
          return throwError(
            () => new Error('Service.index(): error retrieving . ' + err)
          );
        })
      );
  }

  findAllByUserCohort(cohort: String): Observable<Question[]> {
    return this.http
      .get<Question[]>(
        this.url + 'api/students/questions/cohort/' + cohort,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving .', err);
          return throwError(
            () => new Error('Service.index(): error retrieving . ' + err)
          );
        })
      );
  }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.authService.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }
}
