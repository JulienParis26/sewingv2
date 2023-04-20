import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabricTypes, NewFabricTypes } from '../fabric-types.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabricTypes for edit and NewFabricTypesFormGroupInput for create.
 */
type FabricTypesFormGroupInput = IFabricTypes | PartialWithRequiredKeyOf<NewFabricTypes>;

type FabricTypesFormDefaults = Pick<NewFabricTypes, 'id' | 'fabrics'>;

type FabricTypesFormGroupContent = {
  id: FormControl<IFabricTypes['id'] | NewFabricTypes['id']>;
  name: FormControl<IFabricTypes['name']>;
  code: FormControl<IFabricTypes['code']>;
  description: FormControl<IFabricTypes['description']>;
  fabrics: FormControl<IFabricTypes['fabrics']>;
};

export type FabricTypesFormGroup = FormGroup<FabricTypesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricTypesFormService {
  createFabricTypesFormGroup(fabricTypes: FabricTypesFormGroupInput = { id: null }): FabricTypesFormGroup {
    const fabricTypesRawValue = {
      ...this.getFormDefaults(),
      ...fabricTypes,
    };
    return new FormGroup<FabricTypesFormGroupContent>({
      id: new FormControl(
        { value: fabricTypesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricTypesRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(fabricTypesRawValue.code, {
        validators: [Validators.required, Validators.minLength(5)],
      }),
      description: new FormControl(fabricTypesRawValue.description, {
        validators: [Validators.maxLength(1024)],
      }),
      fabrics: new FormControl(fabricTypesRawValue.fabrics ?? []),
    });
  }

  getFabricTypes(form: FabricTypesFormGroup): IFabricTypes | NewFabricTypes {
    return form.getRawValue() as IFabricTypes | NewFabricTypes;
  }

  resetForm(form: FabricTypesFormGroup, fabricTypes: FabricTypesFormGroupInput): void {
    const fabricTypesRawValue = { ...this.getFormDefaults(), ...fabricTypes };
    form.reset(
      {
        ...fabricTypesRawValue,
        id: { value: fabricTypesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricTypesFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
