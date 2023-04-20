import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric-types.test-samples';

import { FabricTypesFormService } from './fabric-types-form.service';

describe('FabricTypes Form Service', () => {
  let service: FabricTypesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricTypesFormService);
  });

  describe('Service methods', () => {
    describe('createFabricTypesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricTypesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });

      it('passing IFabricTypes should create a new form with FormGroup', () => {
        const formGroup = service.createFabricTypesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });
    });

    describe('getFabricTypes', () => {
      it('should return NewFabricTypes for default FabricTypes initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricTypesFormGroup(sampleWithNewData);

        const fabricTypes = service.getFabricTypes(formGroup) as any;

        expect(fabricTypes).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabricTypes for empty FabricTypes initial value', () => {
        const formGroup = service.createFabricTypesFormGroup();

        const fabricTypes = service.getFabricTypes(formGroup) as any;

        expect(fabricTypes).toMatchObject({});
      });

      it('should return IFabricTypes', () => {
        const formGroup = service.createFabricTypesFormGroup(sampleWithRequiredData);

        const fabricTypes = service.getFabricTypes(formGroup) as any;

        expect(fabricTypes).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabricTypes should not enable id FormControl', () => {
        const formGroup = service.createFabricTypesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabricTypes should disable id FormControl', () => {
        const formGroup = service.createFabricTypesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
