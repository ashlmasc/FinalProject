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
  styleUrl: './users.component.css',
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  selected: User | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

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
    console.log(user);
    this.selected = user;
  }

  updateUser(id: number): void {
    if (!this.selected) {
      alert('You must have a user selected to update the user.');
      return;
    }

    if (this?.selected?.cohort !== null) {
      this.selected.cohort = this.selected.cohort.toUpperCase();
    }

    if (this?.selected?.role !== null) {
      this.selected.role = this.selected.role.toLowerCase();
    }

    let user: User = new User();
    let foundUser: boolean = false;

    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].id === id) {
        user = this.users[i];
        foundUser = true;
        break;
      }
    }
    if (!foundUser) {
      alert('DANGER!!');
      return;
    }
    this.userService.update(user).subscribe({
      next: (updatedUser) => {
        const index = this.users.findIndex((t) => t.id === updatedUser.id);
        if (index !== -1) {
          this.users[index] = updatedUser;
        }
        this.selected = null;

        console.log('Users after update:', this.users);
      },
      error: (err) => {
        console.error('Error updating user:', err);
      },
    });
  }

  deleteUser(user: User): void {
    user.enabled = false;
    this.updateUser(user.id);
  }

  displayTable(): void {
    this.selected = null;
  }
}
