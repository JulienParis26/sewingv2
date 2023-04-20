import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterialsService } from '../service/materials.service';

import { MaterialsComponent } from './materials.component';

describe('Materials Management Component', () => {
  let comp: MaterialsComponent;
  let fixture: ComponentFixture<MaterialsComponent>;
  let service: MaterialsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'materials', component: MaterialsComponent }]), HttpClientTestingModule],
      declarations: [MaterialsComponent],
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
      .overrideTemplate(MaterialsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterialsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MaterialsService);

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
    expect(comp.materials?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to materialsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMaterialsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMaterialsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
