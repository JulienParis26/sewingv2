import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricUsesFormService } from './fabric-uses-form.service';
import { FabricUsesService } from '../service/fabric-uses.service';
import { IFabricUses } from '../fabric-uses.model';

import { FabricUsesUpdateComponent } from './fabric-uses-update.component';

describe('FabricUses Management Update Component', () => {
  let comp: FabricUsesUpdateComponent;
  let fixture: ComponentFixture<FabricUsesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricUsesFormService: FabricUsesFormService;
  let fabricUsesService: FabricUsesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricUsesUpdateComponent],
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
      .overrideTemplate(FabricUsesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricUsesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricUsesFormService = TestBed.inject(FabricUsesFormService);
    fabricUsesService = TestBed.inject(FabricUsesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fabricUses: IFabricUses = { id: 456 };

      activatedRoute.data = of({ fabricUses });
      comp.ngOnInit();

      expect(comp.fabricUses).toEqual(fabricUses);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricUses>>();
      const fabricUses = { id: 123 };
      jest.spyOn(fabricUsesFormService, 'getFabricUses').mockReturnValue(fabricUses);
      jest.spyOn(fabricUsesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricUses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricUses }));
      saveSubject.complete();

      // THEN
      expect(fabricUsesFormService.getFabricUses).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricUsesService.update).toHaveBeenCalledWith(expect.objectContaining(fabricUses));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricUses>>();
      const fabricUses = { id: 123 };
      jest.spyOn(fabricUsesFormService, 'getFabricUses').mockReturnValue({ id: null });
      jest.spyOn(fabricUsesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricUses: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricUses }));
      saveSubject.complete();

      // THEN
      expect(fabricUsesFormService.getFabricUses).toHaveBeenCalled();
      expect(fabricUsesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricUses>>();
      const fabricUses = { id: 123 };
      jest.spyOn(fabricUsesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricUses });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricUsesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
