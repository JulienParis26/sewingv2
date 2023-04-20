import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fabric-editor.test-samples';

import { FabricEditorFormService } from './fabric-editor-form.service';

describe('FabricEditor Form Service', () => {
  let service: FabricEditorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FabricEditorFormService);
  });

  describe('Service methods', () => {
    describe('createFabricEditorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFabricEditorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            printDate: expect.any(Object),
            number: expect.any(Object),
            editor: expect.any(Object),
            language: expect.any(Object),
            price: expect.any(Object),
            image: expect.any(Object),
          })
        );
      });

      it('passing IFabricEditor should create a new form with FormGroup', () => {
        const formGroup = service.createFabricEditorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            printDate: expect.any(Object),
            number: expect.any(Object),
            editor: expect.any(Object),
            language: expect.any(Object),
            price: expect.any(Object),
            image: expect.any(Object),
          })
        );
      });
    });

    describe('getFabricEditor', () => {
      it('should return NewFabricEditor for default FabricEditor initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFabricEditorFormGroup(sampleWithNewData);

        const fabricEditor = service.getFabricEditor(formGroup) as any;

        expect(fabricEditor).toMatchObject(sampleWithNewData);
      });

      it('should return NewFabricEditor for empty FabricEditor initial value', () => {
        const formGroup = service.createFabricEditorFormGroup();

        const fabricEditor = service.getFabricEditor(formGroup) as any;

        expect(fabricEditor).toMatchObject({});
      });

      it('should return IFabricEditor', () => {
        const formGroup = service.createFabricEditorFormGroup(sampleWithRequiredData);

        const fabricEditor = service.getFabricEditor(formGroup) as any;

        expect(fabricEditor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFabricEditor should not enable id FormControl', () => {
        const formGroup = service.createFabricEditorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFabricEditor should disable id FormControl', () => {
        const formGroup = service.createFabricEditorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
