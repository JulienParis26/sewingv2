import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FabricLabelsDetailComponent } from './fabric-labels-detail.component';

describe('FabricLabels Management Detail Component', () => {
  let comp: FabricLabelsDetailComponent;
  let fixture: ComponentFixture<FabricLabelsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FabricLabelsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fabricLabels: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FabricLabelsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FabricLabelsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fabricLabels on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fabricLabels).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
