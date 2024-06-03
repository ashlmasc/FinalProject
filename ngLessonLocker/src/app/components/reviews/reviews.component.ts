import { Component, OnInit } from '@angular/core';
import { Question } from '../../models/question';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InstructorService } from '../../services/instructor.service';
import { User } from '../../models/user';
import { QuizQuestion } from '../../models/quiz-question';

@Component({
  selector: 'app-reviews',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reviews.component.html',
  styleUrl: './reviews.component.css',
})
export class ReviewsComponent implements OnInit {
  questions: Question[] = [];
  selected: Question | null = null;
  isLoaded: boolean = false;
  users: User[] = [];

  questionSearch: string = '';

  questionEnabledSearch: boolean = false;

  selectedQuiz: string | null = null;

  tag: string = '';
  username: string = '';
  cohort: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private instructorService: InstructorService
  ) {}

  showQuestionDetail(question: Question) {
    this.router.navigateByUrl(`review/${question.id}`);
  }

  ngOnInit(): void {
    this.getAllQuestions();
    this.checkSelectedQuiz;
  }

  filterQuestionsKeepWithTag(tag: string): Question[] {
    // tags: [ {id: 1, title: 'jfop' }];
    return this.questions.filter((question) => {
      let found = false;
      question.tags.forEach((t: any) => {
        if (t.title.indexOf(tag) > -1) {
          found = true;
        }
      });
      return found;
    });
  }

  getAllQuestions() {
    this.isLoaded = false;
    this.instructorService.findAll().subscribe({
      next: (questions: Question[]) => {
        this.questions = questions;
        if (this.tag !== '') {
          this.questions = this.filterQuestionsKeepWithTag(this.tag);
        }
        console.log(this.questions);
        this.isLoaded = true;
      },
      error: () => {
        this.isLoaded = true;
        return (err: Error) => console.error('Observer got an error: ' + err);
      },
    });
  }

  addQuestionToQuiz(question: Question) {
    let quizIdString = localStorage.getItem('selectedQuizId');
    if (quizIdString == null) {
      return;
    }
    let quizId: number = parseInt(quizIdString);
    if (!quizId) {
      return;
    }
    this.instructorService.addQuestionToQuiz(quizId, question.id).subscribe({
      next: (quizQuestion: QuizQuestion) => {
        console.log(quizQuestion);
        alert('Question added to quiz');
      },
      error: () => {
        return (err: Error) => {
          console.error('Observer got an error: ' + err);
          alert(JSON.stringify(err));
          return;
        };
      },
    });
  }

  checkSelectedQuizForQuestion(qId: number): boolean {
    // TODO : Implement this function
    let found = false;
    return found;
  }

  checkSelectedQuiz(): string | null {
    let selectedQuiz = localStorage.getItem('selectedQuiz');
    if (selectedQuiz !== null) {
      this.selectedQuiz = JSON.parse(selectedQuiz);
    } else {
      this.selectedQuiz = null;
    }
    return localStorage.getItem('selectedQuizId');
  }

  // remove question from quiz
  removeQuestionFromQuiz(question: Question) {
    let quizIdString = localStorage.getItem('selectedQuizId');
    if (quizIdString == null) {
      return;
    }
    let quizId: number = parseInt(quizIdString);
    if (!quizId) {
      return;
    }
    this.instructorService
      .removeQuestionFromQuiz(quizId, question.id)
      .subscribe({
        next: (quizQuestion: QuizQuestion) => {
          console.log(quizQuestion);
          alert('Question removed from quiz');
        },
        error: () => {
          return (err: Error) => {
            console.error('Observer got an error: ' + err);
            alert(JSON.stringify(err));
            return;
          };
        },
      });
  }

  getAllQuestionsByUserUsername(username: string) {
    this.isLoaded = false;
    this.instructorService.findAllByUserUsername(username).subscribe({
      next: (questions: Question[]) => {
        this.questions = questions;
        console.log(this.questions);
        this.isLoaded = true;
      },
      error: () => {
        this.isLoaded = true;
        return (err: Error) => console.error('Observer got an error: ' + err);
      },
    });
  }

  queryByCohort(cohort: string) {
    this.getAllQuestionsByUserCohort(cohort);
    this.getAllUsersByUserCohort(cohort);
  }

  getAllQuestionsByUserCohort(cohort: string) {
    this.isLoaded = false;
    this.instructorService.findAllByUserCohort(cohort).subscribe({
      next: (questions: Question[]) => {
        this.questions = questions;
        console.log(this.questions);
        this.isLoaded = true;
      },
      error: () => {
        this.isLoaded = true;
        return (err: Error) => console.error('Observer got an error: ' + err);
      },
    });
  }

  getAllUsersByUserCohort(cohort: string) {
    this.isLoaded = false;
    this.instructorService.findAllUsersByCohort(cohort).subscribe({
      next: (users: User[]) => {
        this.users = users;
        console.log(this.users);
        this.isLoaded = true;
      },
      error: () => {
        this.isLoaded = true;
        return (err: Error) => console.error('Observer got an error: ' + err);
      },
    });
  }
}
