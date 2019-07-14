import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BestResultComponent } from './best-result.component';

describe('BestResultComponent', () => {
  let component: BestResultComponent;
  let fixture: ComponentFixture<BestResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BestResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BestResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
