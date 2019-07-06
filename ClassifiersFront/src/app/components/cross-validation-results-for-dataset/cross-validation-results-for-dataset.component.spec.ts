import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CrossValidationResultsForDatasetComponent } from './cross-validation-results-for-dataset.component';

describe('CrossValidationResultsForDatasetComponent', () => {
  let component: CrossValidationResultsForDatasetComponent;
  let fixture: ComponentFixture<CrossValidationResultsForDatasetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CrossValidationResultsForDatasetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CrossValidationResultsForDatasetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
