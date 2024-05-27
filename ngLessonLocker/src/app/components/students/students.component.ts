import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student';
import { StudentService } from '../../services/student.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './students.component.html',
  styleUrl: './students.component.css'
})
export class StudentsComponent implements OnInit {
  students: Student[] = [];

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.studentService.getAll().subscribe({
      next: (students) => this.students = students,
      error: (err) => console.error('Error loading students:', err)
    });
  }

  deleteStudent(id: number): void {
    this.studentService.delete(id).subscribe({
      next: () => this.loadStudents(),
      error: (err) => console.error('Error deleting student:', err)
    });
  }
}
