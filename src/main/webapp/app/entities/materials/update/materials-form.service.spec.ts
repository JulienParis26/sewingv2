import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../materials.test-samples';

import { MaterialsFormService } from './materials-form.service';

describe('Materials Form Service', () => {
  let service: MaterialsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MaterialsFormService);
  });

  describe('Service methods', () => {
    describe('createMaterialsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMaterialsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            webSite: expect.any(Object),
            description: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });

      it('passing IMaterials should create a new form with FormGroup', () => {
        const formGroup = service.createMaterialsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            webSite: expect.any(Object),
            description: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });
    });

    describe('getMaterials', () => {
      it('should return NewMaterials for default Materials initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMaterialsFormGroup(sampleWithNewData);

        const materials = service.getMaterials(formGroup) as any;

        expect(materials).toMatchObject(sampleWithNewData);
      });

      it('should return NewMaterials for empty Materials initial value', () => {
        const formGroup = service.createMaterialsFormGroup();

        const materials = service.getMaterials(formGroup) as any;

        expect(materials).toMatchObject({});
      });

      it('should return IMaterials', () => {
        const formGroup = service.createMaterialsFormGroup(sampleWithRequiredData);

        const materials = service.getMaterials(formGroup) as any;

        expect(materials).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMaterials should not enable id FormControl', () => {
        const formGroup = service.createMaterialsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMaterials should disable id FormControl', () => {
        const formGroup = service.createMaterialsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
