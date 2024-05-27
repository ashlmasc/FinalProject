import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Question } from '../models/question';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  index(): Observable<Question[]> {
    return this.http.get<Question[]>(this.url + "api/questions", this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving questions:', err);
        return throwError(() => new Error('QuestionService.index(): error retrieving questions: ' + err));
      })
    );
  }

  show(id: number): Observable<Question> {
    return this.http.get<Question>(this.url + "api/questions/" + id, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving question:', err);
        return throwError(() => new Error('QuestionService.show(): error retrieving question: ' + err));
      })
    );
  }

  create(question: Question): Observable<Question> {
    return this.http.post<Question>(this.url + "api/questions", question, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error creating question:', err);
        return throwError(() => new Error('QuestionService.create(): error creating question: ' + err));
      })
    );
  }

  update(id: number, question: Question): Observable<Question> {
    return this.http.put<Question>(this.url + "api/questions/" + id, question, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error updating question:', err);
        return throwError(() => new Error('QuestionService.update(): error updating question: ' + err));
      })
    );
  }


  delete(id: number): Observable<void> {
    return this.http.delete<void>(this.url + "api/questions/" + id, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error deleting question:', err);
        return throwError(() => new Error('QuestionService.delete(): error deleting question: ' + err));
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
