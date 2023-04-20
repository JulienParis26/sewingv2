import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FabricSellerDetailComponent } from './fabric-seller-detail.component';

describe('FabricSeller Management Detail Component', () => {
  let comp: FabricSellerDetailComponent;
  let fixture: ComponentFixture<FabricSellerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FabricSellerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fabricSeller: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FabricSellerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FabricSellerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fabricSeller on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fabricSeller).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
