import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { Quiz } from '../models/quiz';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) {}

  show(id: number): Observable<Quiz> {
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
