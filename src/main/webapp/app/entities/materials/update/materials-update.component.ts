import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MaterialsFormService, MaterialsFormGroup } from './materials-form.service';
import { IMaterials } from '../materials.model';
import { MaterialsService } from '../service/materials.service';

@Component({
  selector: 'jhi-materials-update',
  templateUrl: './materials-update.component.html',
})
export class MaterialsUpdateComponent implements OnInit {
  isSaving = false;
  materials: IMaterials | null = null;

  editForm: MaterialsFormGroup = this.materialsFormService.createMaterialsFormGroup();

  constructor(
    protected materialsService: MaterialsService,
    protected materialsFormService: MaterialsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materials }) => {
      this.materials = materials;
      if (materials) {
        this.updateForm(materials);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materials = this.materialsFormService.getMaterials(this.editForm);
    if (materials.id !== null) {
      this.subscribeToSaveResponse(this.materialsService.update(materials));
    } else {
      this.subscribeToSaveResponse(this.materialsService.create(materials));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterials>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(materials: IMaterials): void {
    this.materials = materials;
    this.materialsFormService.resetForm(this.editForm, materials);
  }
}
