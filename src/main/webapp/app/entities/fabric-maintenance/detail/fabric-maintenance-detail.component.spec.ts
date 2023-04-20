import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FabricMaintenanceDetailComponent } from './fabric-maintenance-detail.component';

describe('FabricMaintenance Management Detail Component', () => {
  let comp: FabricMaintenanceDetailComponent;
  let fixture: ComponentFixture<FabricMaintenanceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FabricMaintenanceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fabricMaintenance: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FabricMaintenanceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FabricMaintenanceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fabricMaintenance on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fabricMaintenance).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
