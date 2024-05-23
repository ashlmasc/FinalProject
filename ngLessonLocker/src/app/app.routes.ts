import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AboutComponent } from './components/about/about.component';
import { QuestionComponent } from './components/question/question.component';
import { QuestionEditComponent } from './components/question-edit/question-edit.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'question', component: QuestionComponent },
  { path: 'question-edit/:id', component: QuestionEditComponent },
  { path: '**', component: NotFoundComponent },
];
