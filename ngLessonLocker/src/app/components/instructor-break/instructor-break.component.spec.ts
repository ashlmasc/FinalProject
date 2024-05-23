import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructorBreakComponent } from './instructor-break.component';

describe('InstructorBreakComponent', () => {
  let component: InstructorBreakComponent;
  let fixture: ComponentFixture<InstructorBreakComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstructorBreakComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InstructorBreakComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
