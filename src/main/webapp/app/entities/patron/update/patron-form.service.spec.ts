import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../patron.test-samples';

import { PatronFormService } from './patron-form.service';

describe('Patron Form Service', () => {
  let service: PatronFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PatronFormService);
  });

  describe('Service methods', () => {
    describe('createPatronFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPatronFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            ref: expect.any(Object),
            type: expect.any(Object),
            sexe: expect.any(Object),
            buyDate: expect.any(Object),
            publicationDate: expect.any(Object),
            creator: expect.any(Object),
            difficultLevel: expect.any(Object),
            fabricQualification: expect.any(Object),
            requiredFootage: expect.any(Object),
            requiredLaize: expect.any(Object),
            clothingType: expect.any(Object),
            price: expect.any(Object),
            pictureTechnicalDrawing: expect.any(Object),
            carriedPicture1: expect.any(Object),
            carriedPicture2: expect.any(Object),
          })
        );
      });

      it('passing IPatron should create a new form with FormGroup', () => {
        const formGroup = service.createPatronFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            ref: expect.any(Object),
            type: expect.any(Object),
            sexe: expect.any(Object),
            buyDate: expect.any(Object),
            publicationDate: expect.any(Object),
            creator: expect.any(Object),
            difficultLevel: expect.any(Object),
            fabricQualification: expect.any(Object),
            requiredFootage: expect.any(Object),
            requiredLaize: expect.any(Object),
            clothingType: expect.any(Object),
            price: expect.any(Object),
            pictureTechnicalDrawing: expect.any(Object),
            carriedPicture1: expect.any(Object),
            carriedPicture2: expect.any(Object),
          })
        );
      });
    });

    describe('getPatron', () => {
      it('should return NewPatron for default Patron initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPatronFormGroup(sampleWithNewData);

        const patron = service.getPatron(formGroup) as any;

        expect(patron).toMatchObject(sampleWithNewData);
      });

      it('should return NewPatron for empty Patron initial value', () => {
        const formGroup = service.createPatronFormGroup();

        const patron = service.getPatron(formGroup) as any;

        expect(patron).toMatchObject({});
      });

      it('should return IPatron', () => {
        const formGroup = service.createPatronFormGroup(sampleWithRequiredData);

        const patron = service.getPatron(formGroup) as any;

        expect(patron).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPatron should not enable id FormControl', () => {
        const formGroup = service.createPatronFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPatron should disable id FormControl', () => {
        const formGroup = service.createPatronFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
