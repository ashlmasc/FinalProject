import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { QuestionService } from '../../services/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from '../../models/question';

@Component({
  selector: 'app-question-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './question-edit.component.html',
  styleUrl: './question-edit.component.css'
})
export class QuestionEditComponent implements OnInit {

  selected: Question | null = null;

  constructor(private http: HttpClient, private authService: AuthService, private questionService: QuestionService, private activatedRoute: ActivatedRoute, private router: Router) { }
  
  
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        let questionIdStr = params.get("id");
        console.log(questionIdStr);
        if (questionIdStr) {
          let questionId = parseInt(questionIdStr);
          if (isNaN(questionId)) {
            console.log(questionId);
            this.router.navigateByUrl('/not-found');
          } else {
            this.questionService.show(questionId).subscribe({
              next: (question) => {
                console.log(question);
                if (question) {
                  this.selected = question;
                  console.log(this.selected);
                } else {
                  this.router.navigateByUrl('/not-found');
                }
              },
              error: () => this.router.navigateByUrl('/not-found')
            });
          }
        }
      }
    });

}
}
