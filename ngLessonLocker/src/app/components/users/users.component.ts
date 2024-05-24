import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {

 users: User[] = [];

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

}
