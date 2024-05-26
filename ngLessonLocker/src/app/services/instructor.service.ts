import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Question } from '../models/question';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InstructorService {
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) {}

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
