import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Question } from '../../models/question';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from '../../services/question.service';

@Component({
  selector: 'app-questions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './questions.component.html',
  styleUrl: './questions.component.css'
})
export class QuestionsComponent implements OnInit {

  questions: Question[] = [];
  selected: Question | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private questionService: QuestionService
  ) {}
  
  ngOnInit(): void {
    this.getAllQuestions();
  }

  getAllQuestions() {
    this.questionService.index().subscribe({
      next: (questions: Question[]) => {
        this.questions = questions;
        console.log(this.questions);
      },
      error: (err: Error) => console.error('Observer got an error: ' + err),
    });
  }

  showQuestionDetail(question: Question){
    this.router.navigateByUrl(`question/${question.id}`);
  }

}
