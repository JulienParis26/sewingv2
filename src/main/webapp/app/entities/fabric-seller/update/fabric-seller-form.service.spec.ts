import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric-seller.test-samples';

import { FabricSellerFormService } from './fabric-seller-form.service';

describe('FabricSeller Form Service', () => {
  let service: FabricSellerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricSellerFormService);
  });

  describe('Service methods', () => {
    describe('createFabricSellerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricSellerFormGroup();

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

      it('passing IFabricSeller should create a new form with FormGroup', () => {
        const formGroup = service.createFabricSellerFormGroup(sampleWithRequiredData);

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

    describe('getFabricSeller', () => {
      it('should return NewFabricSeller for default FabricSeller initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricSellerFormGroup(sampleWithNewData);

        const fabricSeller = service.getFabricSeller(formGroup) as any;

        expect(fabricSeller).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabricSeller for empty FabricSeller initial value', () => {
        const formGroup = service.createFabricSellerFormGroup();

        const fabricSeller = service.getFabricSeller(formGroup) as any;

        expect(fabricSeller).toMatchObject({});
      });

      it('should return IFabricSeller', () => {
        const formGroup = service.createFabricSellerFormGroup(sampleWithRequiredData);

        const fabricSeller = service.getFabricSeller(formGroup) as any;

        expect(fabricSeller).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabricSeller should not enable id FormControl', () => {
        const formGroup = service.createFabricSellerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabricSeller should disable id FormControl', () => {
        const formGroup = service.createFabricSellerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
