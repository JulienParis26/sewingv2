import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric.test-samples';

import { FabricFormService } from './fabric-form.service';

describe('Fabric Form Service', () => {
  let service: FabricFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricFormService);
  });

  describe('Service methods', () => {
    describe('createFabricFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            ref: expect.any(Object),
            uni: expect.any(Object),
            buySize: expect.any(Object),
            elastic: expect.any(Object),
            elasticRate: expect.any(Object),
            rating: expect.any(Object),
            colorName: expect.any(Object),
            color1: expect.any(Object),
            color2: expect.any(Object),
            color3: expect.any(Object),
            laize: expect.any(Object),
            meterPrice: expect.any(Object),
            meterBuy: expect.any(Object),
            meterInStock: expect.any(Object),
            buyDate: expect.any(Object),
            gramPerMeter: expect.any(Object),
            sizeMin: expect.any(Object),
            sizeMax: expect.any(Object),
            image1: expect.any(Object),
            image2: expect.any(Object),
            image3: expect.any(Object),
            from: expect.any(Object),
            fabricTypes: expect.any(Object),
            materials: expect.any(Object),
            uses: expect.any(Object),
            maintenances: expect.any(Object),
            labels: expect.any(Object),
            sellers: expect.any(Object),
            projects: expect.any(Object),
          })
        );
      });

      it('passing IFabric should create a new form with FormGroup', () => {
        const formGroup = service.createFabricFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            ref: expect.any(Object),
            uni: expect.any(Object),
            buySize: expect.any(Object),
            elastic: expect.any(Object),
            elasticRate: expect.any(Object),
            rating: expect.any(Object),
            colorName: expect.any(Object),
            color1: expect.any(Object),
            color2: expect.any(Object),
            color3: expect.any(Object),
            laize: expect.any(Object),
            meterPrice: expect.any(Object),
            meterBuy: expect.any(Object),
            meterInStock: expect.any(Object),
            buyDate: expect.any(Object),
            gramPerMeter: expect.any(Object),
            sizeMin: expect.any(Object),
            sizeMax: expect.any(Object),
            image1: expect.any(Object),
            image2: expect.any(Object),
            image3: expect.any(Object),
            from: expect.any(Object),
            fabricTypes: expect.any(Object),
            materials: expect.any(Object),
            uses: expect.any(Object),
            maintenances: expect.any(Object),
            labels: expect.any(Object),
            sellers: expect.any(Object),
            projects: expect.any(Object),
          })
        );
      });
    });

    describe('getFabric', () => {
      it('should return NewFabric for default Fabric initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricFormGroup(sampleWithNewData);

        const fabric = service.getFabric(formGroup) as any;

        expect(fabric).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabric for empty Fabric initial value', () => {
        const formGroup = service.createFabricFormGroup();

        const fabric = service.getFabric(formGroup) as any;

        expect(fabric).toMatchObject({});
      });

      it('should return IFabric', () => {
        const formGroup = service.createFabricFormGroup(sampleWithRequiredData);

        const fabric = service.getFabric(formGroup) as any;

        expect(fabric).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabric should not enable id FormControl', () => {
        const formGroup = service.createFabricFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabric should disable id FormControl', () => {
        const formGroup = service.createFabricFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
