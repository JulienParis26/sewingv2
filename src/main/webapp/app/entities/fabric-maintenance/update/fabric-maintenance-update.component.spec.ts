import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricMaintenanceFormService } from './fabric-maintenance-form.service';
import { FabricMaintenanceService } from '../service/fabric-maintenance.service';
import { IFabricMaintenance } from '../fabric-maintenance.model';

import { FabricMaintenanceUpdateComponent } from './fabric-maintenance-update.component';

describe('FabricMaintenance Management Update Component', () => {
  let comp: FabricMaintenanceUpdateComponent;
  let fixture: ComponentFixture<FabricMaintenanceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricMaintenanceFormService: FabricMaintenanceFormService;
  let fabricMaintenanceService: FabricMaintenanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricMaintenanceUpdateComponent],
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
      .overrideTemplate(FabricMaintenanceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricMaintenanceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricMaintenanceFormService = TestBed.inject(FabricMaintenanceFormService);
    fabricMaintenanceService = TestBed.inject(FabricMaintenanceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fabricMaintenance: IFabricMaintenance = { id: 456 };

      activatedRoute.data = of({ fabricMaintenance });
      comp.ngOnInit();

      expect(comp.fabricMaintenance).toEqual(fabricMaintenance);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricMaintenance>>();
      const fabricMaintenance = { id: 123 };
      jest.spyOn(fabricMaintenanceFormService, 'getFabricMaintenance').mockReturnValue(fabricMaintenance);
      jest.spyOn(fabricMaintenanceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricMaintenance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricMaintenance }));
      saveSubject.complete();

      // THEN
      expect(fabricMaintenanceFormService.getFabricMaintenance).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricMaintenanceService.update).toHaveBeenCalledWith(expect.objectContaining(fabricMaintenance));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricMaintenance>>();
      const fabricMaintenance = { id: 123 };
      jest.spyOn(fabricMaintenanceFormService, 'getFabricMaintenance').mockReturnValue({ id: null });
      jest.spyOn(fabricMaintenanceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricMaintenance: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricMaintenance }));
      saveSubject.complete();

      // THEN
      expect(fabricMaintenanceFormService.getFabricMaintenance).toHaveBeenCalled();
      expect(fabricMaintenanceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricMaintenance>>();
      const fabricMaintenance = { id: 123 };
      jest.spyOn(fabricMaintenanceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricMaintenance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricMaintenanceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
