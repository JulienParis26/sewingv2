import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric-maintenance.test-samples';

import { FabricMaintenanceFormService } from './fabric-maintenance-form.service';

describe('FabricMaintenance Form Service', () => {
  let service: FabricMaintenanceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricMaintenanceFormService);
  });

  describe('Service methods', () => {
    describe('createFabricMaintenanceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricMaintenanceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });

      it('passing IFabricMaintenance should create a new form with FormGroup', () => {
        const formGroup = service.createFabricMaintenanceFormGroup(sampleWithRequiredData);

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

    describe('getFabricMaintenance', () => {
      it('should return NewFabricMaintenance for default FabricMaintenance initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricMaintenanceFormGroup(sampleWithNewData);

        const fabricMaintenance = service.getFabricMaintenance(formGroup) as any;

        expect(fabricMaintenance).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabricMaintenance for empty FabricMaintenance initial value', () => {
        const formGroup = service.createFabricMaintenanceFormGroup();

        const fabricMaintenance = service.getFabricMaintenance(formGroup) as any;

        expect(fabricMaintenance).toMatchObject({});
      });

      it('should return IFabricMaintenance', () => {
        const formGroup = service.createFabricMaintenanceFormGroup(sampleWithRequiredData);

        const fabricMaintenance = service.getFabricMaintenance(formGroup) as any;

        expect(fabricMaintenance).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabricMaintenance should not enable id FormControl', () => {
        const formGroup = service.createFabricMaintenanceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabricMaintenance should disable id FormControl', () => {
        const formGroup = service.createFabricMaintenanceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
