import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BoostingResultsForDatasetComponent } from './boosting-results-for-dataset.component';

describe('BoostingResultsForDatasetComponent', () => {
  let component: BoostingResultsForDatasetComponent;
  let fixture: ComponentFixture<BoostingResultsForDatasetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BoostingResultsForDatasetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BoostingResultsForDatasetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
