import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FabricUsesService } from '../service/fabric-uses.service';

import { FabricUsesComponent } from './fabric-uses.component';

describe('FabricUses Management Component', () => {
  let comp: FabricUsesComponent;
  let fixture: ComponentFixture<FabricUsesComponent>;
  let service: FabricUsesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'fabric-uses', component: FabricUsesComponent }]), HttpClientTestingModule],
      declarations: [FabricUsesComponent],
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
      .overrideTemplate(FabricUsesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricUsesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FabricUsesService);

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
    expect(comp.fabricUses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to fabricUsesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getFabricUsesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFabricUsesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
