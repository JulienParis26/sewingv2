import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricFormService } from './fabric-form.service';
import { FabricService } from '../service/fabric.service';
import { IFabric } from '../fabric.model';
import { IFabricEditor } from 'app/entities/fabric-editor/fabric-editor.model';
import { FabricEditorService } from 'app/entities/fabric-editor/service/fabric-editor.service';
import { IFabricTypes } from 'app/entities/fabric-types/fabric-types.model';
import { FabricTypesService } from 'app/entities/fabric-types/service/fabric-types.service';
import { IMaterials } from 'app/entities/materials/materials.model';
import { MaterialsService } from 'app/entities/materials/service/materials.service';
import { IFabricUses } from 'app/entities/fabric-uses/fabric-uses.model';
import { FabricUsesService } from 'app/entities/fabric-uses/service/fabric-uses.service';
import { IFabricMaintenance } from 'app/entities/fabric-maintenance/fabric-maintenance.model';
import { FabricMaintenanceService } from 'app/entities/fabric-maintenance/service/fabric-maintenance.service';
import { IFabricLabels } from 'app/entities/fabric-labels/fabric-labels.model';
import { FabricLabelsService } from 'app/entities/fabric-labels/service/fabric-labels.service';

import { FabricUpdateComponent } from './fabric-update.component';

describe('Fabric Management Update Component', () => {
  let comp: FabricUpdateComponent;
  let fixture: ComponentFixture<FabricUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricFormService: FabricFormService;
  let fabricService: FabricService;
  let fabricEditorService: FabricEditorService;
  let fabricTypesService: FabricTypesService;
  let materialsService: MaterialsService;
  let fabricUsesService: FabricUsesService;
  let fabricMaintenanceService: FabricMaintenanceService;
  let fabricLabelsService: FabricLabelsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricUpdateComponent],
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
      .overrideTemplate(FabricUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricFormService = TestBed.inject(FabricFormService);
    fabricService = TestBed.inject(FabricService);
    fabricEditorService = TestBed.inject(FabricEditorService);
    fabricTypesService = TestBed.inject(FabricTypesService);
    materialsService = TestBed.inject(MaterialsService);
    fabricUsesService = TestBed.inject(FabricUsesService);
    fabricMaintenanceService = TestBed.inject(FabricMaintenanceService);
    fabricLabelsService = TestBed.inject(FabricLabelsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FabricEditor query and add missing value', () => {
      const fabric: IFabric = { id: 456 };
      const from: IFabricEditor = { id: 50274 };
      fabric.from = from;

      const fabricEditorCollection: IFabricEditor[] = [{ id: 12900 }];
      jest.spyOn(fabricEditorService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricEditorCollection })));
      const additionalFabricEditors = [from];
      const expectedCollection: IFabricEditor[] = [...additionalFabricEditors, ...fabricEditorCollection];
      jest.spyOn(fabricEditorService, 'addFabricEditorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(fabricEditorService.query).toHaveBeenCalled();
      expect(fabricEditorService.addFabricEditorToCollectionIfMissing).toHaveBeenCalledWith(
        fabricEditorCollection,
        ...additionalFabricEditors.map(expect.objectContaining)
      );
      expect(comp.fabricEditorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FabricTypes query and add missing value', () => {
      const fabric: IFabric = { id: 456 };
      const fabricTypes: IFabricTypes[] = [{ id: 29200 }];
      fabric.fabricTypes = fabricTypes;

      const fabricTypesCollection: IFabricTypes[] = [{ id: 5916 }];
      jest.spyOn(fabricTypesService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricTypesCollection })));
      const additionalFabricTypes = [...fabricTypes];
      const expectedCollection: IFabricTypes[] = [...additionalFabricTypes, ...fabricTypesCollection];
      jest.spyOn(fabricTypesService, 'addFabricTypesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(fabricTypesService.query).toHaveBeenCalled();
      expect(fabricTypesService.addFabricTypesToCollectionIfMissing).toHaveBeenCalledWith(
        fabricTypesCollection,
        ...additionalFabricTypes.map(expect.objectContaining)
      );
      expect(comp.fabricTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Materials query and add missing value', () => {
      const fabric: IFabric = { id: 456 };
      const materials: IMaterials[] = [{ id: 59077 }];
      fabric.materials = materials;

      const materialsCollection: IMaterials[] = [{ id: 8906 }];
      jest.spyOn(materialsService, 'query').mockReturnValue(of(new HttpResponse({ body: materialsCollection })));
      const additionalMaterials = [...materials];
      const expectedCollection: IMaterials[] = [...additionalMaterials, ...materialsCollection];
      jest.spyOn(materialsService, 'addMaterialsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(materialsService.query).toHaveBeenCalled();
      expect(materialsService.addMaterialsToCollectionIfMissing).toHaveBeenCalledWith(
        materialsCollection,
        ...additionalMaterials.map(expect.objectContaining)
      );
      expect(comp.materialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FabricUses query and add missing value', () => {
      const fabric: IFabric = { id: 456 };
      const uses: IFabricUses[] = [{ id: 27847 }];
      fabric.uses = uses;

      const fabricUsesCollection: IFabricUses[] = [{ id: 25839 }];
      jest.spyOn(fabricUsesService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricUsesCollection })));
      const additionalFabricUses = [...uses];
      const expectedCollection: IFabricUses[] = [...additionalFabricUses, ...fabricUsesCollection];
      jest.spyOn(fabricUsesService, 'addFabricUsesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(fabricUsesService.query).toHaveBeenCalled();
      expect(fabricUsesService.addFabricUsesToCollectionIfMissing).toHaveBeenCalledWith(
        fabricUsesCollection,
        ...additionalFabricUses.map(expect.objectContaining)
      );
      expect(comp.fabricUsesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FabricMaintenance query and add missing value', () => {
      const fabric: IFabric = { id: 456 };
      const maintenances: IFabricMaintenance[] = [{ id: 10399 }];
      fabric.maintenances = maintenances;

      const fabricMaintenanceCollection: IFabricMaintenance[] = [{ id: 86253 }];
      jest.spyOn(fabricMaintenanceService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricMaintenanceCollection })));
      const additionalFabricMaintenances = [...maintenances];
      const expectedCollection: IFabricMaintenance[] = [...additionalFabricMaintenances, ...fabricMaintenanceCollection];
      jest.spyOn(fabricMaintenanceService, 'addFabricMaintenanceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(fabricMaintenanceService.query).toHaveBeenCalled();
      expect(fabricMaintenanceService.addFabricMaintenanceToCollectionIfMissing).toHaveBeenCalledWith(
        fabricMaintenanceCollection,
        ...additionalFabricMaintenances.map(expect.objectContaining)
      );
      expect(comp.fabricMaintenancesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FabricLabels query and add missing value', () => {
      const fabric: IFabric = { id: 456 };
      const labels: IFabricLabels[] = [{ id: 74003 }];
      fabric.labels = labels;

      const fabricLabelsCollection: IFabricLabels[] = [{ id: 20179 }];
      jest.spyOn(fabricLabelsService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricLabelsCollection })));
      const additionalFabricLabels = [...labels];
      const expectedCollection: IFabricLabels[] = [...additionalFabricLabels, ...fabricLabelsCollection];
      jest.spyOn(fabricLabelsService, 'addFabricLabelsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(fabricLabelsService.query).toHaveBeenCalled();
      expect(fabricLabelsService.addFabricLabelsToCollectionIfMissing).toHaveBeenCalledWith(
        fabricLabelsCollection,
        ...additionalFabricLabels.map(expect.objectContaining)
      );
      expect(comp.fabricLabelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fabric: IFabric = { id: 456 };
      const from: IFabricEditor = { id: 62235 };
      fabric.from = from;
      const fabricTypes: IFabricTypes = { id: 66945 };
      fabric.fabricTypes = [fabricTypes];
      const materials: IMaterials = { id: 64839 };
      fabric.materials = [materials];
      const uses: IFabricUses = { id: 8728 };
      fabric.uses = [uses];
      const maintenances: IFabricMaintenance = { id: 37031 };
      fabric.maintenances = [maintenances];
      const labels: IFabricLabels = { id: 83472 };
      fabric.labels = [labels];

      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      expect(comp.fabricEditorsSharedCollection).toContain(from);
      expect(comp.fabricTypesSharedCollection).toContain(fabricTypes);
      expect(comp.materialsSharedCollection).toContain(materials);
      expect(comp.fabricUsesSharedCollection).toContain(uses);
      expect(comp.fabricMaintenancesSharedCollection).toContain(maintenances);
      expect(comp.fabricLabelsSharedCollection).toContain(labels);
      expect(comp.fabric).toEqual(fabric);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabric>>();
      const fabric = { id: 123 };
      jest.spyOn(fabricFormService, 'getFabric').mockReturnValue(fabric);
      jest.spyOn(fabricService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabric }));
      saveSubject.complete();

      // THEN
      expect(fabricFormService.getFabric).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricService.update).toHaveBeenCalledWith(expect.objectContaining(fabric));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabric>>();
      const fabric = { id: 123 };
      jest.spyOn(fabricFormService, 'getFabric').mockReturnValue({ id: null });
      jest.spyOn(fabricService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabric: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabric }));
      saveSubject.complete();

      // THEN
      expect(fabricFormService.getFabric).toHaveBeenCalled();
      expect(fabricService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabric>>();
      const fabric = { id: 123 };
      jest.spyOn(fabricService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabric });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFabricEditor', () => {
      it('Should forward to fabricEditorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricEditorService, 'compareFabricEditor');
        comp.compareFabricEditor(entity, entity2);
        expect(fabricEditorService.compareFabricEditor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFabricTypes', () => {
      it('Should forward to fabricTypesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricTypesService, 'compareFabricTypes');
        comp.compareFabricTypes(entity, entity2);
        expect(fabricTypesService.compareFabricTypes).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMaterials', () => {
      it('Should forward to materialsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(materialsService, 'compareMaterials');
        comp.compareMaterials(entity, entity2);
        expect(materialsService.compareMaterials).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFabricUses', () => {
      it('Should forward to fabricUsesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricUsesService, 'compareFabricUses');
        comp.compareFabricUses(entity, entity2);
        expect(fabricUsesService.compareFabricUses).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFabricMaintenance', () => {
      it('Should forward to fabricMaintenanceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricMaintenanceService, 'compareFabricMaintenance');
        comp.compareFabricMaintenance(entity, entity2);
        expect(fabricMaintenanceService.compareFabricMaintenance).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFabricLabels', () => {
      it('Should forward to fabricLabelsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricLabelsService, 'compareFabricLabels');
        comp.compareFabricLabels(entity, entity2);
        expect(fabricLabelsService.compareFabricLabels).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
