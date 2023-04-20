import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FabricTypesDetailComponent } from './fabric-types-detail.component';

describe('FabricTypes Management Detail Component', () => {
  let comp: FabricTypesDetailComponent;
  let fixture: ComponentFixture<FabricTypesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FabricTypesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fabricTypes: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FabricTypesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FabricTypesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fabricTypes on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fabricTypes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
