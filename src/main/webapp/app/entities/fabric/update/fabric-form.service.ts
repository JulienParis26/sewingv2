import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabric, NewFabric } from '../fabric.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabric for edit and NewFabricFormGroupInput for create.
 */
type FabricFormGroupInput = IFabric | PartialWithRequiredKeyOf<NewFabric>;

type FabricFormDefaults = Pick<
  NewFabric,
  'id' | 'uni' | 'elastic' | 'fabricTypes' | 'materials' | 'uses' | 'maintenances' | 'labels' | 'sellers' | 'projects'
>;

type FabricFormGroupContent = {
  id: FormControl<IFabric['id'] | NewFabric['id']>;
  name: FormControl<IFabric['name']>;
  ref: FormControl<IFabric['ref']>;
  uni: FormControl<IFabric['uni']>;
  buySize: FormControl<IFabric['buySize']>;
  elastic: FormControl<IFabric['elastic']>;
  elasticRate: FormControl<IFabric['elasticRate']>;
  rating: FormControl<IFabric['rating']>;
  colorName: FormControl<IFabric['colorName']>;
  color1: FormControl<IFabric['color1']>;
  color2: FormControl<IFabric['color2']>;
  color3: FormControl<IFabric['color3']>;
  laize: FormControl<IFabric['laize']>;
  meterPrice: FormControl<IFabric['meterPrice']>;
  meterBuy: FormControl<IFabric['meterBuy']>;
  meterInStock: FormControl<IFabric['meterInStock']>;
  buyDate: FormControl<IFabric['buyDate']>;
  gramPerMeter: FormControl<IFabric['gramPerMeter']>;
  sizeMin: FormControl<IFabric['sizeMin']>;
  sizeMax: FormControl<IFabric['sizeMax']>;
  image1: FormControl<IFabric['image1']>;
  image1ContentType: FormControl<IFabric['image1ContentType']>;
  image2: FormControl<IFabric['image2']>;
  image2ContentType: FormControl<IFabric['image2ContentType']>;
  image3: FormControl<IFabric['image3']>;
  image3ContentType: FormControl<IFabric['image3ContentType']>;
  from: FormControl<IFabric['from']>;
  fabricTypes: FormControl<IFabric['fabricTypes']>;
  materials: FormControl<IFabric['materials']>;
  uses: FormControl<IFabric['uses']>;
  maintenances: FormControl<IFabric['maintenances']>;
  labels: FormControl<IFabric['labels']>;
  sellers: FormControl<IFabric['sellers']>;
  projects: FormControl<IFabric['projects']>;
};

export type FabricFormGroup = FormGroup<FabricFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricFormService {
  createFabricFormGroup(fabric: FabricFormGroupInput = { id: null }): FabricFormGroup {
    const fabricRawValue = {
      ...this.getFormDefaults(),
      ...fabric,
    };
    return new FormGroup<FabricFormGroupContent>({
      id: new FormControl(
        { value: fabricRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricRawValue.name, {
        validators: [Validators.required],
      }),
      ref: new FormControl(fabricRawValue.ref),
      uni: new FormControl(fabricRawValue.uni, {
        validators: [Validators.required],
      }),
      buySize: new FormControl(fabricRawValue.buySize),
      elastic: new FormControl(fabricRawValue.elastic, {
        validators: [Validators.required],
      }),
      elasticRate: new FormControl(fabricRawValue.elasticRate),
      rating: new FormControl(fabricRawValue.rating, {
        validators: [Validators.min(1), Validators.max(5)],
      }),
      colorName: new FormControl(fabricRawValue.colorName),
      color1: new FormControl(fabricRawValue.color1, {
        validators: [Validators.required],
      }),
      color2: new FormControl(fabricRawValue.color2),
      color3: new FormControl(fabricRawValue.color3),
      laize: new FormControl(fabricRawValue.laize),
      meterPrice: new FormControl(fabricRawValue.meterPrice),
      meterBuy: new FormControl(fabricRawValue.meterBuy),
      meterInStock: new FormControl(fabricRawValue.meterInStock),
      buyDate: new FormControl(fabricRawValue.buyDate),
      gramPerMeter: new FormControl(fabricRawValue.gramPerMeter),
      sizeMin: new FormControl(fabricRawValue.sizeMin),
      sizeMax: new FormControl(fabricRawValue.sizeMax),
      image1: new FormControl(fabricRawValue.image1, {
        validators: [Validators.required],
      }),
      image1ContentType: new FormControl(fabricRawValue.image1ContentType),
      image2: new FormControl(fabricRawValue.image2),
      image2ContentType: new FormControl(fabricRawValue.image2ContentType),
      image3: new FormControl(fabricRawValue.image3),
      image3ContentType: new FormControl(fabricRawValue.image3ContentType),
      from: new FormControl(fabricRawValue.from),
      fabricTypes: new FormControl(fabricRawValue.fabricTypes ?? []),
      materials: new FormControl(fabricRawValue.materials ?? []),
      uses: new FormControl(fabricRawValue.uses ?? []),
      maintenances: new FormControl(fabricRawValue.maintenances ?? []),
      labels: new FormControl(fabricRawValue.labels ?? []),
      sellers: new FormControl(fabricRawValue.sellers ?? []),
      projects: new FormControl(fabricRawValue.projects ?? []),
    });
  }

  getFabric(form: FabricFormGroup): IFabric | NewFabric {
    return form.getRawValue() as IFabric | NewFabric;
  }

  resetForm(form: FabricFormGroup, fabric: FabricFormGroupInput): void {
    const fabricRawValue = { ...this.getFormDefaults(), ...fabric };
    form.reset(
      {
        ...fabricRawValue,
        id: { value: fabricRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricFormDefaults {
    return {
      id: null,
      uni: false,
      elastic: false,
      fabricTypes: [],
      materials: [],
      uses: [],
      maintenances: [],
      labels: [],
      sellers: [],
      projects: [],
    };
  }
}
