import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabricMaintenance, NewFabricMaintenance } from '../fabric-maintenance.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabricMaintenance for edit and NewFabricMaintenanceFormGroupInput for create.
 */
type FabricMaintenanceFormGroupInput = IFabricMaintenance | PartialWithRequiredKeyOf<NewFabricMaintenance>;

type FabricMaintenanceFormDefaults = Pick<NewFabricMaintenance, 'id' | 'fabrics'>;

type FabricMaintenanceFormGroupContent = {
  id: FormControl<IFabricMaintenance['id'] | NewFabricMaintenance['id']>;
  name: FormControl<IFabricMaintenance['name']>;
  code: FormControl<IFabricMaintenance['code']>;
  fabrics: FormControl<IFabricMaintenance['fabrics']>;
};

export type FabricMaintenanceFormGroup = FormGroup<FabricMaintenanceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricMaintenanceFormService {
  createFabricMaintenanceFormGroup(fabricMaintenance: FabricMaintenanceFormGroupInput = { id: null }): FabricMaintenanceFormGroup {
    const fabricMaintenanceRawValue = {
      ...this.getFormDefaults(),
      ...fabricMaintenance,
    };
    return new FormGroup<FabricMaintenanceFormGroupContent>({
      id: new FormControl(
        { value: fabricMaintenanceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricMaintenanceRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(fabricMaintenanceRawValue.code, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      fabrics: new FormControl(fabricMaintenanceRawValue.fabrics ?? []),
    });
  }

  getFabricMaintenance(form: FabricMaintenanceFormGroup): IFabricMaintenance | NewFabricMaintenance {
    return form.getRawValue() as IFabricMaintenance | NewFabricMaintenance;
  }

  resetForm(form: FabricMaintenanceFormGroup, fabricMaintenance: FabricMaintenanceFormGroupInput): void {
    const fabricMaintenanceRawValue = { ...this.getFormDefaults(), ...fabricMaintenance };
    form.reset(
      {
        ...fabricMaintenanceRawValue,
        id: { value: fabricMaintenanceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricMaintenanceFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
