import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FabricUsesDetailComponent } from './fabric-uses-detail.component';

describe('FabricUses Management Detail Component', () => {
  let comp: FabricUsesDetailComponent;
  let fixture: ComponentFixture<FabricUsesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FabricUsesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fabricUses: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FabricUsesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FabricUsesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fabricUses on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fabricUses).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
