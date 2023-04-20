import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FabricMaintenanceService } from '../service/fabric-maintenance.service';

import { FabricMaintenanceComponent } from './fabric-maintenance.component';

describe('FabricMaintenance Management Component', () => {
  let comp: FabricMaintenanceComponent;
  let fixture: ComponentFixture<FabricMaintenanceComponent>;
  let service: FabricMaintenanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'fabric-maintenance', component: FabricMaintenanceComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [FabricMaintenanceComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(FabricMaintenanceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricMaintenanceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FabricMaintenanceService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.fabricMaintenances?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to fabricMaintenanceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getFabricMaintenanceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFabricMaintenanceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
