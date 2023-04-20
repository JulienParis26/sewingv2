import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabricSeller, NewFabricSeller } from '../fabric-seller.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabricSeller for edit and NewFabricSellerFormGroupInput for create.
 */
type FabricSellerFormGroupInput = IFabricSeller | PartialWithRequiredKeyOf<NewFabricSeller>;

type FabricSellerFormDefaults = Pick<NewFabricSeller, 'id' | 'fabrics'>;

type FabricSellerFormGroupContent = {
  id: FormControl<IFabricSeller['id'] | NewFabricSeller['id']>;
  name: FormControl<IFabricSeller['name']>;
  webSite: FormControl<IFabricSeller['webSite']>;
  description: FormControl<IFabricSeller['description']>;
  fabrics: FormControl<IFabricSeller['fabrics']>;
};

export type FabricSellerFormGroup = FormGroup<FabricSellerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricSellerFormService {
  createFabricSellerFormGroup(fabricSeller: FabricSellerFormGroupInput = { id: null }): FabricSellerFormGroup {
    const fabricSellerRawValue = {
      ...this.getFormDefaults(),
      ...fabricSeller,
    };
    return new FormGroup<FabricSellerFormGroupContent>({
      id: new FormControl(
        { value: fabricSellerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricSellerRawValue.name, {
        validators: [Validators.required],
      }),
      webSite: new FormControl(fabricSellerRawValue.webSite),
      description: new FormControl(fabricSellerRawValue.description, {
        validators: [Validators.maxLength(1024)],
      }),
      fabrics: new FormControl(fabricSellerRawValue.fabrics ?? []),
    });
  }

  getFabricSeller(form: FabricSellerFormGroup): IFabricSeller | NewFabricSeller {
    return form.getRawValue() as IFabricSeller | NewFabricSeller;
  }

  resetForm(form: FabricSellerFormGroup, fabricSeller: FabricSellerFormGroupInput): void {
    const fabricSellerRawValue = { ...this.getFormDefaults(), ...fabricSeller };
    form.reset(
      {
        ...fabricSellerRawValue,
        id: { value: fabricSellerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricSellerFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
