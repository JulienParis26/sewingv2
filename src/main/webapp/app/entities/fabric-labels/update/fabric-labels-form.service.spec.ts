import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric-labels.test-samples';

import { FabricLabelsFormService } from './fabric-labels-form.service';

describe('FabricLabels Form Service', () => {
  let service: FabricLabelsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricLabelsFormService);
  });

  describe('Service methods', () => {
    describe('createFabricLabelsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricLabelsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            fabrics: expect.any(Object),
          })
        );
      });

      it('passing IFabricLabels should create a new form with FormGroup', () => {
        const formGroup = service.createFabricLabelsFormGroup(sampleWithRequiredData);

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

    describe('getFabricLabels', () => {
      it('should return NewFabricLabels for default FabricLabels initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricLabelsFormGroup(sampleWithNewData);

        const fabricLabels = service.getFabricLabels(formGroup) as any;

        expect(fabricLabels).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabricLabels for empty FabricLabels initial value', () => {
        const formGroup = service.createFabricLabelsFormGroup();

        const fabricLabels = service.getFabricLabels(formGroup) as any;

        expect(fabricLabels).toMatchObject({});
      });

      it('should return IFabricLabels', () => {
        const formGroup = service.createFabricLabelsFormGroup(sampleWithRequiredData);

        const fabricLabels = service.getFabricLabels(formGroup) as any;

        expect(fabricLabels).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabricLabels should not enable id FormControl', () => {
        const formGroup = service.createFabricLabelsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabricLabels should disable id FormControl', () => {
        const formGroup = service.createFabricLabelsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
