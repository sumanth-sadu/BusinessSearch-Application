import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupTempComponent } from './popup-temp.component';

describe('PopupTempComponent', () => {
  let component: PopupTempComponent;
  let fixture: ComponentFixture<PopupTempComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopupTempComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopupTempComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
