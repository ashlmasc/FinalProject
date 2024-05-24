import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  //FIELDS
  user:  User = new User();

  //CONSTRUCTOR
  constructor(private authService: AuthService, private router: Router){}

  //LFECYCLE HOOKS
  ngOnInit(): void{

  }

  //OTHER METHODS
  login(user: User): void {
   // console.log(user); //data binding confirmation
    this.authService.login(this.user.username, this.user.password).subscribe({
      next: () => {
        console.log('Login successful');
        this.router.navigateByUrl('home');
      },
      error: (error) => console.error('Login failed', error)
    });
  }
}
