import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricSellerFormService } from './fabric-seller-form.service';
import { FabricSellerService } from '../service/fabric-seller.service';
import { IFabricSeller } from '../fabric-seller.model';
import { IFabric } from 'app/entities/fabric/fabric.model';
import { FabricService } from 'app/entities/fabric/service/fabric.service';

import { FabricSellerUpdateComponent } from './fabric-seller-update.component';

describe('FabricSeller Management Update Component', () => {
  let comp: FabricSellerUpdateComponent;
  let fixture: ComponentFixture<FabricSellerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricSellerFormService: FabricSellerFormService;
  let fabricSellerService: FabricSellerService;
  let fabricService: FabricService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricSellerUpdateComponent],
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
      .overrideTemplate(FabricSellerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricSellerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricSellerFormService = TestBed.inject(FabricSellerFormService);
    fabricSellerService = TestBed.inject(FabricSellerService);
    fabricService = TestBed.inject(FabricService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Fabric query and add missing value', () => {
      const fabricSeller: IFabricSeller = { id: 456 };
      const fabrics: IFabric[] = [{ id: 41076 }];
      fabricSeller.fabrics = fabrics;

      const fabricCollection: IFabric[] = [{ id: 44330 }];
      jest.spyOn(fabricService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricCollection })));
      const additionalFabrics = [...fabrics];
      const expectedCollection: IFabric[] = [...additionalFabrics, ...fabricCollection];
      jest.spyOn(fabricService, 'addFabricToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabricSeller });
      comp.ngOnInit();

      expect(fabricService.query).toHaveBeenCalled();
      expect(fabricService.addFabricToCollectionIfMissing).toHaveBeenCalledWith(
        fabricCollection,
        ...additionalFabrics.map(expect.objectContaining)
      );
      expect(comp.fabricsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fabricSeller: IFabricSeller = { id: 456 };
      const fabric: IFabric = { id: 74727 };
      fabricSeller.fabrics = [fabric];

      activatedRoute.data = of({ fabricSeller });
      comp.ngOnInit();

      expect(comp.fabricsSharedCollection).toContain(fabric);
      expect(comp.fabricSeller).toEqual(fabricSeller);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricSeller>>();
      const fabricSeller = { id: 123 };
      jest.spyOn(fabricSellerFormService, 'getFabricSeller').mockReturnValue(fabricSeller);
      jest.spyOn(fabricSellerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricSeller });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricSeller }));
      saveSubject.complete();

      // THEN
      expect(fabricSellerFormService.getFabricSeller).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricSellerService.update).toHaveBeenCalledWith(expect.objectContaining(fabricSeller));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricSeller>>();
      const fabricSeller = { id: 123 };
      jest.spyOn(fabricSellerFormService, 'getFabricSeller').mockReturnValue({ id: null });
      jest.spyOn(fabricSellerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricSeller: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricSeller }));
      saveSubject.complete();

      // THEN
      expect(fabricSellerFormService.getFabricSeller).toHaveBeenCalled();
      expect(fabricSellerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricSeller>>();
      const fabricSeller = { id: 123 };
      jest.spyOn(fabricSellerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricSeller });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricSellerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFabric', () => {
      it('Should forward to fabricService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricService, 'compareFabric');
        comp.compareFabric(entity, entity2);
        expect(fabricService.compareFabric).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
