import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { InstructorService } from '../../services/instructor.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Quiz } from '../../models/quiz';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-quizzes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './quizzes.component.html',
  styleUrl: './quizzes.component.css',
})
export class QuizzesComponent implements OnInit {
  quizzes: Quiz[] = [];

  ngOnInit(): void {
    this.loadQuizzes();
  }

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private instructorService: InstructorService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  loadQuizzes(): void {
    this.instructorService.loadQuizzes().subscribe({
      next: (quizzes: Quiz[]) => {
        console.log(quizzes);
        if (quizzes) {
          this.quizzes = quizzes;
          console.log(this.quizzes);
          return;
        } else {
          alert('Quizzes not found');
          return this.router.navigateByUrl('/');
        }
      },
      error: (err) => {
        alert('You must be logged in to use this page.');
        console.log(err?.error?.message || err?.message || err);
        return this.router.navigateByUrl('/login');
        return;
      },
    });
  }
}
