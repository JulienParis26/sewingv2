import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFabricEditor, NewFabricEditor } from '../fabric-editor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFabricEditor for edit and NewFabricEditorFormGroupInput for create.
 */
type FabricEditorFormGroupInput = IFabricEditor | PartialWithRequiredKeyOf<NewFabricEditor>;

type FabricEditorFormDefaults = Pick<NewFabricEditor, 'id'>;

type FabricEditorFormGroupContent = {
  id: FormControl<IFabricEditor['id'] | NewFabricEditor['id']>;
  name: FormControl<IFabricEditor['name']>;
  printDate: FormControl<IFabricEditor['printDate']>;
  number: FormControl<IFabricEditor['number']>;
  editor: FormControl<IFabricEditor['editor']>;
  language: FormControl<IFabricEditor['language']>;
  price: FormControl<IFabricEditor['price']>;
  image: FormControl<IFabricEditor['image']>;
  imageContentType: FormControl<IFabricEditor['imageContentType']>;
};

export type FabricEditorFormGroup = FormGroup<FabricEditorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FabricEditorFormService {
  createFabricEditorFormGroup(fabricEditor: FabricEditorFormGroupInput = { id: null }): FabricEditorFormGroup {
    const fabricEditorRawValue = {
      ...this.getFormDefaults(),
      ...fabricEditor,
    };
    return new FormGroup<FabricEditorFormGroupContent>({
      id: new FormControl(
        { value: fabricEditorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fabricEditorRawValue.name),
      printDate: new FormControl(fabricEditorRawValue.printDate),
      number: new FormControl(fabricEditorRawValue.number),
      editor: new FormControl(fabricEditorRawValue.editor),
      language: new FormControl(fabricEditorRawValue.language),
      price: new FormControl(fabricEditorRawValue.price),
      image: new FormControl(fabricEditorRawValue.image),
      imageContentType: new FormControl(fabricEditorRawValue.imageContentType),
    });
  }

  getFabricEditor(form: FabricEditorFormGroup): IFabricEditor | NewFabricEditor {
    return form.getRawValue() as IFabricEditor | NewFabricEditor;
  }

  resetForm(form: FabricEditorFormGroup, fabricEditor: FabricEditorFormGroupInput): void {
    const fabricEditorRawValue = { ...this.getFormDefaults(), ...fabricEditor };
    form.reset(
      {
        ...fabricEditorRawValue,
        id: { value: fabricEditorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FabricEditorFormDefaults {
    return {
      id: null,
    };
  }
}
