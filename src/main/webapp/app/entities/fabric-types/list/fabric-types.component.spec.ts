import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FabricTypesService } from '../service/fabric-types.service';

import { FabricTypesComponent } from './fabric-types.component';

describe('FabricTypes Management Component', () => {
  let comp: FabricTypesComponent;
  let fixture: ComponentFixture<FabricTypesComponent>;
  let service: FabricTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'fabric-types', component: FabricTypesComponent }]), HttpClientTestingModule],
      declarations: [FabricTypesComponent],
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
      .overrideTemplate(FabricTypesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricTypesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FabricTypesService);

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
    expect(comp.fabricTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to fabricTypesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getFabricTypesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFabricTypesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
