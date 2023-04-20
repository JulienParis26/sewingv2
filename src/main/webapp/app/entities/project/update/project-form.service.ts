import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProject, NewProject } from '../project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProject for edit and NewProjectFormGroupInput for create.
 */
type ProjectFormGroupInput = IProject | PartialWithRequiredKeyOf<NewProject>;

type ProjectFormDefaults = Pick<NewProject, 'id' | 'fabrics'>;

type ProjectFormGroupContent = {
  id: FormControl<IProject['id'] | NewProject['id']>;
  name: FormControl<IProject['name']>;
  ref: FormControl<IProject['ref']>;
  creationDate: FormControl<IProject['creationDate']>;
  haberdasheryUse: FormControl<IProject['haberdasheryUse']>;
  accessoryUse: FormControl<IProject['accessoryUse']>;
  image1: FormControl<IProject['image1']>;
  image1ContentType: FormControl<IProject['image1ContentType']>;
  image2: FormControl<IProject['image2']>;
  image2ContentType: FormControl<IProject['image2ContentType']>;
  image3: FormControl<IProject['image3']>;
  image3ContentType: FormControl<IProject['image3ContentType']>;
  image4: FormControl<IProject['image4']>;
  image4ContentType: FormControl<IProject['image4ContentType']>;
  patron: FormControl<IProject['patron']>;
  fabrics: FormControl<IProject['fabrics']>;
};

export type ProjectFormGroup = FormGroup<ProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectFormService {
  createProjectFormGroup(project: ProjectFormGroupInput = { id: null }): ProjectFormGroup {
    const projectRawValue = {
      ...this.getFormDefaults(),
      ...project,
    };
    return new FormGroup<ProjectFormGroupContent>({
      id: new FormControl(
        { value: projectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(projectRawValue.name),
      ref: new FormControl(projectRawValue.ref),
      creationDate: new FormControl(projectRawValue.creationDate),
      haberdasheryUse: new FormControl(projectRawValue.haberdasheryUse),
      accessoryUse: new FormControl(projectRawValue.accessoryUse),
      image1: new FormControl(projectRawValue.image1),
      image1ContentType: new FormControl(projectRawValue.image1ContentType),
      image2: new FormControl(projectRawValue.image2),
      image2ContentType: new FormControl(projectRawValue.image2ContentType),
      image3: new FormControl(projectRawValue.image3),
      image3ContentType: new FormControl(projectRawValue.image3ContentType),
      image4: new FormControl(projectRawValue.image4),
      image4ContentType: new FormControl(projectRawValue.image4ContentType),
      patron: new FormControl(projectRawValue.patron),
      fabrics: new FormControl(projectRawValue.fabrics ?? []),
    });
  }

  getProject(form: ProjectFormGroup): IProject | NewProject {
    return form.getRawValue() as IProject | NewProject;
  }

  resetForm(form: ProjectFormGroup, project: ProjectFormGroupInput): void {
    const projectRawValue = { ...this.getFormDefaults(), ...project };
    form.reset(
      {
        ...projectRawValue,
        id: { value: projectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectFormDefaults {
    return {
      id: null,
      fabrics: [],
    };
  }
}
