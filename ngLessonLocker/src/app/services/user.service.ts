import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';
import { User } from '../models/user';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }
  
  // show(todoId: number): Observable<User> {
  //   return this.http.get<User>(`${this.url}/${userId}`).pipe(
  //     catchError((err: any) => {
  //       console.error('Error retrieving todo:', err);
  //       return throwError(() => new Error('TodoService.show(): error retrieving todo: ' + err));
  //     })
  //   );
  // }

  index(): Observable<User[]> {
    return this.http.get<User[]>(this.url + "api/users", this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('Error retrieving users:', err);
        return throwError(() => new Error('UserService.index(): error retrieving users: ' + err));
      })
    );
  }

  // create(newTodo: Todo): Observable<Todo> {
  //   return this.http.post<Todo>(this.url, newTodo, this.getHttpOptions()).pipe(
  //     catchError((err: any) => {
  //       console.error('Error creating todo:', err);
  //       return throwError(() => new Error('TodoService.create(): error creating todo: ' + err.message));
  //     })
  //   );
  // }

  // update(todo: Todo): Observable<Todo> {
  //   if (todo.completed) {
  //     todo.completeDate = this.datePipe.transform(Date.now(), 'yyyy-MM-dd');
  //   } else {
  //     todo.completeDate = null;
  //   }
  //   return this.http.put<Todo>(`${this.url}/${todo.id}`, todo, this.getHttpOptions()).pipe(
  //     catchError((err: any) => {
  //       console.error('Error updating todo:', err);
  //       return throwError(() => new Error('TodoService.update(): error updating todo: ' + err.message));
  //     })
  //   );
  // }

  // delete(id: number): Observable<void> {
  //   return this.http.delete<void>(`${this.url}/${id}`, this.getHttpOptions()).pipe(
  //     catchError((err: any) => {
  //       console.error('Error deleting todo:', err);
  //       return throwError(() => new Error('TodoService.delete(): error deleting todo: ' + err));
  //     })
  //   );
  // }

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
