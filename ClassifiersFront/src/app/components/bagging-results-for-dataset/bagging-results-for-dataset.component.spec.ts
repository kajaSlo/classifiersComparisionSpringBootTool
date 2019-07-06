import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaggingResultsForDatasetComponent } from './bagging-results-for-dataset.component';

describe('BaggingResultsForDatasetComponent', () => {
  let component: BaggingResultsForDatasetComponent;
  let fixture: ComponentFixture<BaggingResultsForDatasetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaggingResultsForDatasetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaggingResultsForDatasetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
