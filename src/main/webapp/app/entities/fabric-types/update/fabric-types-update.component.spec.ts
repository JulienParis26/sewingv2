import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricTypesFormService } from './fabric-types-form.service';
import { FabricTypesService } from '../service/fabric-types.service';
import { IFabricTypes } from '../fabric-types.model';

import { FabricTypesUpdateComponent } from './fabric-types-update.component';

describe('FabricTypes Management Update Component', () => {
  let comp: FabricTypesUpdateComponent;
  let fixture: ComponentFixture<FabricTypesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricTypesFormService: FabricTypesFormService;
  let fabricTypesService: FabricTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricTypesUpdateComponent],
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
      .overrideTemplate(FabricTypesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricTypesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricTypesFormService = TestBed.inject(FabricTypesFormService);
    fabricTypesService = TestBed.inject(FabricTypesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fabricTypes: IFabricTypes = { id: 456 };

      activatedRoute.data = of({ fabricTypes });
      comp.ngOnInit();

      expect(comp.fabricTypes).toEqual(fabricTypes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricTypes>>();
      const fabricTypes = { id: 123 };
      jest.spyOn(fabricTypesFormService, 'getFabricTypes').mockReturnValue(fabricTypes);
      jest.spyOn(fabricTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricTypes }));
      saveSubject.complete();

      // THEN
      expect(fabricTypesFormService.getFabricTypes).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricTypesService.update).toHaveBeenCalledWith(expect.objectContaining(fabricTypes));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricTypes>>();
      const fabricTypes = { id: 123 };
      jest.spyOn(fabricTypesFormService, 'getFabricTypes').mockReturnValue({ id: null });
      jest.spyOn(fabricTypesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricTypes: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricTypes }));
      saveSubject.complete();

      // THEN
      expect(fabricTypesFormService.getFabricTypes).toHaveBeenCalled();
      expect(fabricTypesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricTypes>>();
      const fabricTypes = { id: 123 };
      jest.spyOn(fabricTypesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricTypes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricTypesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
