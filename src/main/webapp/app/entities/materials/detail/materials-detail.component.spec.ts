import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MaterialsDetailComponent } from './materials-detail.component';

describe('Materials Management Detail Component', () => {
  let comp: MaterialsDetailComponent;
  let fixture: ComponentFixture<MaterialsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaterialsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ materials: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MaterialsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MaterialsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load materials on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.materials).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
