import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {

 users: User[] = [];
  selected: User | null = null;

  constructor (private activatedRoute: ActivatedRoute, private router: Router, private userService: UserService ){}

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.index().subscribe({
      next: (users: User[]) => {
        this.users = users;
        console.log(this.users);
      },
      error: (err: Error) => console.error('Observer got an error: ' + err),
    });
  }

   //functions

   displayUser(user: User): void {
    console.log(user)
    this.selected = user;
  }

  updateUser(): void {
    // if (this.selected) {
    //   const todo = { ...this.selected };
    //   if (todo.completed) {
    //     todo.completeDate = this.datePipe.transform(Date.now(), 'yyyy-MM-dd');
    //   } else {
    //     todo.completeDate = null;
    //   }
      
    //   console.log('Updating todo:', todo); // Log payload for debugging
      
    //   this.todoService.update(todo).subscribe({
    //     next: (updatedTodo) => {
    //       const index = this.todos.findIndex(t => t.id === updatedTodo.id);
    //       if (index !== -1) {
    //         this.todos[index] = updatedTodo; // Update the todo in the list
    //       }
    //       this.selected = null;
    //       this.editTodo = null;
    //       console.log('Todos after update:', this.todos); // Log todos after update
    //     },
    //     error: (err) => {
    //       console.error('Error updating todo:', err);
    //     }
    //   });
    // }
  }

  deleteUser(id: number): void {
    // this.todoService.delete(id).subscribe(() => {
    //   this.todos = this.todos.filter(todo => todo.id !== id);
    // }, error => {
    //   console.error('Error deleting todo', error);
    // });
  }

  displayTable(): void {
    this.selected = null;
  }
  
}
