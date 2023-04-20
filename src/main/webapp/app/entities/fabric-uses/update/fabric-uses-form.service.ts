import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabricUses, NewFabricUses } from '../fabric-uses.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabricUses for edit and NewFabricUsesFormGroupInput for create.
 */
type FabricUsesFormGroupInput = IFabricUses | PartialWithRequiredKeyOf<NewFabricUses>;

type FabricUsesFormDefaults = Pick<NewFabricUses, 'id' | 'fabrics'>;

type FabricUsesFormGroupContent = {
  id: FormControl<IFabricUses['id'] | NewFabricUses['id']>;
  name: FormControl<IFabricUses['name']>;
  code: FormControl<IFabricUses['code']>;
  fabrics: FormControl<IFabricUses['fabrics']>;
};

export type FabricUsesFormGroup = FormGroup<FabricUsesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricUsesFormService {
  createFabricUsesFormGroup(fabricUses: FabricUsesFormGroupInput = { id: null }): FabricUsesFormGroup {
    const fabricUsesRawValue = {
      ...this.getFormDefaults(),
      ...fabricUses,
    };
    return new FormGroup<FabricUsesFormGroupContent>({
      id: new FormControl(
        { value: fabricUsesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricUsesRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(fabricUsesRawValue.code, {
        validators: [Validators.required, Validators.minLength(5)],
      }),
      fabrics: new FormControl(fabricUsesRawValue.fabrics ?? []),
    });
  }

  getFabricUses(form: FabricUsesFormGroup): IFabricUses | NewFabricUses {
    return form.getRawValue() as IFabricUses | NewFabricUses;
  }

  resetForm(form: FabricUsesFormGroup, fabricUses: FabricUsesFormGroupInput): void {
    const fabricUsesRawValue = { ...this.getFormDefaults(), ...fabricUses };
    form.reset(
      {
        ...fabricUsesRawValue,
        id: { value: fabricUsesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricUsesFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
