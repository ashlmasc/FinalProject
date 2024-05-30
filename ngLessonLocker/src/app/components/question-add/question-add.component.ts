import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { QuestionService } from '../../services/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from '../../models/question';
import { Choice } from '../../models/choice';

@Component({
  selector: 'app-question-add',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './question-add.component.html',
  styleUrl: './question-add.component.css',
})
export class QuestionAddComponent implements OnInit {
  selected: Question = new Question();
  newChoice: Choice = new Choice();
  showDetailView: boolean = false;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.selected = new Question(); // Initialize for creating a new question
  }

  saveQuestion(): void {
    if (!this?.selected?.question) {
      alert('Please enter text for the question!');
      return;
    }
    if (!this?.selected?.hint) {
      alert(
        'NOTE: You did not provide a hint for this question. This may make it more difficult for students to answer correctly.'
      );
    }
    if (!this?.selected?.explanation) {
      alert(
        'NOTE: You did not provide an explanation for this question. This may make it more difficult for students to understand why the correct answer is correct.'
      );
    }

    let numberOfCorrectChoices = 0;
    let totalNumberOfChoices = 0;
    for (let i = 0; i < this.selected.choices.length; i++) {
      console.log(i);
      console.log(this.selected.choices[i].content);
      totalNumberOfChoices++;
      if (this.selected.choices[i].correct === true) {
        numberOfCorrectChoices++;
      }
      if (
        !this.selected.choices[i].content ||
        this.selected.choices[i].content == ''
      ) {
        // possible null or empty string
        alert('Please ensure all choices have content!');
        return;
      }
    }
    if (numberOfCorrectChoices !== 1) {
      alert('Please ensure you have exactly one correct choice!');
      return;
    }
    if (totalNumberOfChoices < 2) {
      alert(
        'Please ensure you have at least 2 choices and that one is marked as the correct choice.'
      );
      return;
    }
    this.questionService.create(this.selected).subscribe({
      next: (createdQuestion) => {
        this.selected = createdQuestion;
        this.showDetailView = true; // Show detail view after creating
      },
      error: (err) => console.error('Error creating question:', err),
    });
  }

  //old code that also needed to edit question
  // ngOnInit(): void {
  //   this.activatedRoute.paramMap.subscribe(params => {
  //     const questionIdStr = params.get("id");
  //     if (questionIdStr) {
  //       const questionId = parseInt(questionIdStr);
  //       if (!isNaN(questionId)) {
  //         this.questionService.show(questionId).subscribe({
  //           next: (question) => this.selected = question,
  //           error: () => this.router.navigateByUrl('/not-found')
  //         });
  //       } else {
  //         this.router.navigateByUrl('/not-found');
  //       }
  //     } else {
  //       this.selected = new Question(); // Prepare for creating a new question
  //     }
  //   });
  // }

  //ols code that also edited question
  // saveQuestion(): void {
  //   if (this.selected && this.selected.id) {
  //     this.selected.enabled = false;
  //     this.questionService.update(this.selected.id, this.selected).subscribe({
  //       next: (updatedQuestion) => this.router.navigateByUrl(`/questions/${updatedQuestion.id}`),
  //       error: (err) => console.error('Error updating question:', err)
  //     });
  //   } else if (this.selected) {
  //     this.questionService.create(this.selected).subscribe({
  //       next: (createdQuestion) => this.router.navigateByUrl(`/questions/${createdQuestion.id}`),
  //       error: (err) => console.error('Error creating question:', err)
  //     });
  //   }
  // }

  addChoice(): void {
    if (this.selected) {
      if (!this.newChoice.content || this.newChoice.content === '') {
        alert('Please enter content for the choice!');
        return;
      }
      this.selected.choices.push({ ...this.newChoice });
      this.newChoice = new Choice();
    }
  }

  removeChoice(index: number): void {
    if (this.selected) {
      this.selected.choices.splice(index, 1);
    }
  }

  deleteQuestion(): void {
    if (this.selected && this.selected.id) {
      this.questionService.delete(this.selected.id).subscribe({
        next: () => this.router.navigateByUrl('/questions'),
        error: (err) => console.error('Error deleting question:', err),
      });
    }
  }

  cancel(): void {
    if (this.selected && this.selected.id) {
      this.router.navigateByUrl(`/questions/${this.selected.id}`);
    } else {
      this.router.navigateByUrl('/questions');
    }
  }

  backToQuestions(): void {
    this.router.navigateByUrl('/questions');
  }
}
