import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric-uses.test-samples';

import { FabricUsesFormService } from './fabric-uses-form.service';

describe('FabricUses Form Service', () => {
  let service: FabricUsesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricUsesFormService);
  });

  describe('Service methods', () => {
    describe('createFabricUsesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricUsesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });

      it('passing IFabricUses should create a new form with FormGroup', () => {
        const formGroup = service.createFabricUsesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });
    });

    describe('getFabricUses', () => {
      it('should return NewFabricUses for default FabricUses initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricUsesFormGroup(sampleWithNewData);

        const fabricUses = service.getFabricUses(formGroup) as any;

        expect(fabricUses).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabricUses for empty FabricUses initial value', () => {
        const formGroup = service.createFabricUsesFormGroup();

        const fabricUses = service.getFabricUses(formGroup) as any;

        expect(fabricUses).toMatchObject({});
      });

      it('should return IFabricUses', () => {
        const formGroup = service.createFabricUsesFormGroup(sampleWithRequiredData);

        const fabricUses = service.getFabricUses(formGroup) as any;

        expect(fabricUses).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabricUses should not enable id FormControl', () => {
        const formGroup = service.createFabricUsesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabricUses should disable id FormControl', () => {
        const formGroup = service.createFabricUsesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
