import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FabricTypesFormService, FabricTypesFormGroup } from './fabric-types-form.service';
import { IFabricTypes } from '../fabric-types.model';
import { FabricTypesService } from '../service/fabric-types.service';

@Component({
  selector: 'jhi-fabric-types-update',
  templateUrl: './fabric-types-update.component.html',
})
export class FabricTypesUpdateComponent implements OnInit {
  isSaving = false;
  fabricTypes: IFabricTypes | null = null;

  editForm: FabricTypesFormGroup = this.fabricTypesFormService.createFabricTypesFormGroup();

  constructor(
    protected fabricTypesService: FabricTypesService,
    protected fabricTypesFormService: FabricTypesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricTypes }) => {
      this.fabricTypes = fabricTypes;
      if (fabricTypes) {
        this.updateForm(fabricTypes);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabricTypes = this.fabricTypesFormService.getFabricTypes(this.editForm);
    if (fabricTypes.id !== null) {
      this.subscribeToSaveResponse(this.fabricTypesService.update(fabricTypes));
    } else {
      this.subscribeToSaveResponse(this.fabricTypesService.create(fabricTypes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabricTypes>>): void {
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

  protected updateForm(fabricTypes: IFabricTypes): void {
    this.fabricTypes = fabricTypes;
    this.fabricTypesFormService.resetForm(this.editForm, fabricTypes);
  }
}
