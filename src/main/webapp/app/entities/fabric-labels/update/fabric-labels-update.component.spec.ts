import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricLabelsFormService } from './fabric-labels-form.service';
import { FabricLabelsService } from '../service/fabric-labels.service';
import { IFabricLabels } from '../fabric-labels.model';

import { FabricLabelsUpdateComponent } from './fabric-labels-update.component';

describe('FabricLabels Management Update Component', () => {
  let comp: FabricLabelsUpdateComponent;
  let fixture: ComponentFixture<FabricLabelsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricLabelsFormService: FabricLabelsFormService;
  let fabricLabelsService: FabricLabelsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricLabelsUpdateComponent],
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
      .overrideTemplate(FabricLabelsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricLabelsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricLabelsFormService = TestBed.inject(FabricLabelsFormService);
    fabricLabelsService = TestBed.inject(FabricLabelsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fabricLabels: IFabricLabels = { id: 456 };

      activatedRoute.data = of({ fabricLabels });
      comp.ngOnInit();

      expect(comp.fabricLabels).toEqual(fabricLabels);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricLabels>>();
      const fabricLabels = { id: 123 };
      jest.spyOn(fabricLabelsFormService, 'getFabricLabels').mockReturnValue(fabricLabels);
      jest.spyOn(fabricLabelsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricLabels });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricLabels }));
      saveSubject.complete();

      // THEN
      expect(fabricLabelsFormService.getFabricLabels).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricLabelsService.update).toHaveBeenCalledWith(expect.objectContaining(fabricLabels));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricLabels>>();
      const fabricLabels = { id: 123 };
      jest.spyOn(fabricLabelsFormService, 'getFabricLabels').mockReturnValue({ id: null });
      jest.spyOn(fabricLabelsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricLabels: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricLabels }));
      saveSubject.complete();

      // THEN
      expect(fabricLabelsFormService.getFabricLabels).toHaveBeenCalled();
      expect(fabricLabelsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricLabels>>();
      const fabricLabels = { id: 123 };
      jest.spyOn(fabricLabelsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricLabels });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricLabelsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
