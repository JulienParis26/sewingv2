import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabricLabels, NewFabricLabels } from '../fabric-labels.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabricLabels for edit and NewFabricLabelsFormGroupInput for create.
 */
type FabricLabelsFormGroupInput = IFabricLabels | PartialWithRequiredKeyOf<NewFabricLabels>;

type FabricLabelsFormDefaults = Pick<NewFabricLabels, 'id' | 'fabrics'>;

type FabricLabelsFormGroupContent = {
  id: FormControl<IFabricLabels['id'] | NewFabricLabels['id']>;
  name: FormControl<IFabricLabels['name']>;
  code: FormControl<IFabricLabels['code']>;
  fabrics: FormControl<IFabricLabels['fabrics']>;
};

export type FabricLabelsFormGroup = FormGroup<FabricLabelsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricLabelsFormService {
  createFabricLabelsFormGroup(fabricLabels: FabricLabelsFormGroupInput = { id: null }): FabricLabelsFormGroup {
    const fabricLabelsRawValue = {
      ...this.getFormDefaults(),
      ...fabricLabels,
    };
    return new FormGroup<FabricLabelsFormGroupContent>({
      id: new FormControl(
        { value: fabricLabelsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricLabelsRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(fabricLabelsRawValue.code, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      fabrics: new FormControl(fabricLabelsRawValue.fabrics ?? []),
    });
  }

  getFabricLabels(form: FabricLabelsFormGroup): IFabricLabels | NewFabricLabels {
    return form.getRawValue() as IFabricLabels | NewFabricLabels;
  }

  resetForm(form: FabricLabelsFormGroup, fabricLabels: FabricLabelsFormGroupInput): void {
    const fabricLabelsRawValue = { ...this.getFormDefaults(), ...fabricLabels };
    form.reset(
      {
        ...fabricLabelsRawValue,
        id: { value: fabricLabelsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricLabelsFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
