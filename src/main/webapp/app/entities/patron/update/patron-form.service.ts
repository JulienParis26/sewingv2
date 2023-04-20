import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPatron, NewPatron } from '../patron.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPatron for edit and NewPatronFormGroupInput for create.
 */
type PatronFormGroupInput = IPatron | PartialWithRequiredKeyOf<NewPatron>;

type PatronFormDefaults = Pick<NewPatron, 'id'>;

type PatronFormGroupContent = {
  id: FormControl<IPatron['id'] | NewPatron['id']>;
  name: FormControl<IPatron['name']>;
  ref: FormControl<IPatron['ref']>;
  type: FormControl<IPatron['type']>;
  sexe: FormControl<IPatron['sexe']>;
  buyDate: FormControl<IPatron['buyDate']>;
  publicationDate: FormControl<IPatron['publicationDate']>;
  creator: FormControl<IPatron['creator']>;
  difficultLevel: FormControl<IPatron['difficultLevel']>;
  fabricQualification: FormControl<IPatron['fabricQualification']>;
  requiredFootage: FormControl<IPatron['requiredFootage']>;
  requiredLaize: FormControl<IPatron['requiredLaize']>;
  clothingType: FormControl<IPatron['clothingType']>;
  price: FormControl<IPatron['price']>;
  pictureTechnicalDrawing: FormControl<IPatron['pictureTechnicalDrawing']>;
  pictureTechnicalDrawingContentType: FormControl<IPatron['pictureTechnicalDrawingContentType']>;
  carriedPicture1: FormControl<IPatron['carriedPicture1']>;
  carriedPicture1ContentType: FormControl<IPatron['carriedPicture1ContentType']>;
  carriedPicture2: FormControl<IPatron['carriedPicture2']>;
  carriedPicture2ContentType: FormControl<IPatron['carriedPicture2ContentType']>;
};

export type PatronFormGroup = FormGroup<PatronFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PatronFormService {
  createPatronFormGroup(patron: PatronFormGroupInput = { id: null }): PatronFormGroup {
    const patronRawValue = {
      ...this.getFormDefaults(),
      ...patron,
    };
    return new FormGroup<PatronFormGroupContent>({
      id: new FormControl(
        { value: patronRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(patronRawValue.name, {
        validators: [Validators.required],
      }),
      ref: new FormControl(patronRawValue.ref),
      type: new FormControl(patronRawValue.type),
      sexe: new FormControl(patronRawValue.sexe),
      buyDate: new FormControl(patronRawValue.buyDate),
      publicationDate: new FormControl(patronRawValue.publicationDate),
      creator: new FormControl(patronRawValue.creator),
      difficultLevel: new FormControl(patronRawValue.difficultLevel),
      fabricQualification: new FormControl(patronRawValue.fabricQualification),
      requiredFootage: new FormControl(patronRawValue.requiredFootage),
      requiredLaize: new FormControl(patronRawValue.requiredLaize),
      clothingType: new FormControl(patronRawValue.clothingType),
      price: new FormControl(patronRawValue.price),
      pictureTechnicalDrawing: new FormControl(patronRawValue.pictureTechnicalDrawing),
      pictureTechnicalDrawingContentType: new FormControl(patronRawValue.pictureTechnicalDrawingContentType),
      carriedPicture1: new FormControl(patronRawValue.carriedPicture1, {
        validators: [Validators.required],
      }),
      carriedPicture1ContentType: new FormControl(patronRawValue.carriedPicture1ContentType),
      carriedPicture2: new FormControl(patronRawValue.carriedPicture2),
      carriedPicture2ContentType: new FormControl(patronRawValue.carriedPicture2ContentType),
    });
  }

  getPatron(form: PatronFormGroup): IPatron | NewPatron {
    return form.getRawValue() as IPatron | NewPatron;
  }

  resetForm(form: PatronFormGroup, patron: PatronFormGroupInput): void {
    const patronRawValue = { ...this.getFormDefaults(), ...patron };
    form.reset(
      {
        ...patronRawValue,
        id: { value: patronRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PatronFormDefaults {
    return {
      id: null,
    };
  }
}
