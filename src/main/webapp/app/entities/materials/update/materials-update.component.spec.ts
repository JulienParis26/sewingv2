import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MaterialsFormService } from './materials-form.service';
import { MaterialsService } from '../service/materials.service';
import { IMaterials } from '../materials.model';

import { MaterialsUpdateComponent } from './materials-update.component';

describe('Materials Management Update Component', () => {
  let comp: MaterialsUpdateComponent;
  let fixture: ComponentFixture<MaterialsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let materialsFormService: MaterialsFormService;
  let materialsService: MaterialsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MaterialsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MaterialsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterialsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    materialsFormService = TestBed.inject(MaterialsFormService);
    materialsService = TestBed.inject(MaterialsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const materials: IMaterials = { id: 456 };

      activatedRoute.data = of({ materials });
      comp.ngOnInit();

      expect(comp.materials).toEqual(materials);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterials>>();
      const materials = { id: 123 };
      jest.spyOn(materialsFormService, 'getMaterials').mockReturnValue(materials);
      jest.spyOn(materialsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materials });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materials }));
      saveSubject.complete();

      // THEN
      expect(materialsFormService.getMaterials).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(materialsService.update).toHaveBeenCalledWith(expect.objectContaining(materials));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterials>>();
      const materials = { id: 123 };
      jest.spyOn(materialsFormService, 'getMaterials').mockReturnValue({ id: null });
      jest.spyOn(materialsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materials: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materials }));
      saveSubject.complete();

      // THEN
      expect(materialsFormService.getMaterials).toHaveBeenCalled();
      expect(materialsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMaterials>>();
      const materials = { id: 123 };
      jest.spyOn(materialsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materials });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(materialsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
