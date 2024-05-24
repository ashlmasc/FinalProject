import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
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
        console.error('Error retrieving questions:', err);
        return throwError(() => new Error('QuestionService.index(): error retrieving questions: ' + err));
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
