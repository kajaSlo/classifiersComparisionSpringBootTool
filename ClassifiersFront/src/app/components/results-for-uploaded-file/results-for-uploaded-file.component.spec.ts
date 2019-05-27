import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultsForUploadedFileComponent } from './results-for-uploaded-file.component';

describe('ResultsForUploadedFileComponent', () => {
  let component: ResultsForUploadedFileComponent;
  let fixture: ComponentFixture<ResultsForUploadedFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultsForUploadedFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultsForUploadedFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
