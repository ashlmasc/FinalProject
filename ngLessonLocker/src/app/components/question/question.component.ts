import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Question } from '../../models/question';

@Component({
  selector: 'app-question',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './question.component.html',
  styleUrl: './question.component.css',
})
export class QuestionComponent implements OnInit {
  @Input() selectedQuestion: Question | null = null;

  ngOnInit(): void {
    console.log(this.selectedQuestion);
  }

  enableQuestion() {
    alert('TODO - Enable question');
  }

  disableQuestion() {
    alert('TODO - Disable question');
  }

  editQuestion() {
    alert('TODO - Edit question');
  }
}
