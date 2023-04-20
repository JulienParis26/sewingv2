import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMaterials, NewMaterials } from '../materials.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMaterials for edit and NewMaterialsFormGroupInput for create.
 */
type MaterialsFormGroupInput = IMaterials | PartialWithRequiredKeyOf<NewMaterials>;

type MaterialsFormDefaults = Pick<NewMaterials, 'id' | 'fabrics'>;

type MaterialsFormGroupContent = {
  id: FormControl<IMaterials['id'] | NewMaterials['id']>;
  name: FormControl<IMaterials['name']>;
  webSite: FormControl<IMaterials['webSite']>;
  description: FormControl<IMaterials['description']>;
  fabrics: FormControl<IMaterials['fabrics']>;
};

export type MaterialsFormGroup = FormGroup<MaterialsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MaterialsFormService {
  createMaterialsFormGroup(materials: MaterialsFormGroupInput = { id: null }): MaterialsFormGroup {
    const materialsRawValue = {
      ...this.getFormDefaults(),
      ...materials,
    };
    return new FormGroup<MaterialsFormGroupContent>({
      id: new FormControl(
        { value: materialsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(materialsRawValue.name, {
        validators: [Validators.required],
      }),
      webSite: new FormControl(materialsRawValue.webSite),
      description: new FormControl(materialsRawValue.description, {
        validators: [Validators.maxLength(1024)],
      }),
      fabrics: new FormControl(materialsRawValue.fabrics ?? []),
    });
  }

  getMaterials(form: MaterialsFormGroup): IMaterials | NewMaterials {
    return form.getRawValue() as IMaterials | NewMaterials;
  }

  resetForm(form: MaterialsFormGroup, materials: MaterialsFormGroupInput): void {
    const materialsRawValue = { ...this.getFormDefaults(), ...materials };
    form.reset(
      {
        ...materialsRawValue,
        id: { value: materialsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MaterialsFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
