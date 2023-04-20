import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FabricLabelsFormService, FabricLabelsFormGroup } from './fabric-labels-form.service';
import { IFabricLabels } from '../fabric-labels.model';
import { FabricLabelsService } from '../service/fabric-labels.service';

@Component({
  selector: 'jhi-fabric-labels-update',
  templateUrl: './fabric-labels-update.component.html',
})
export class FabricLabelsUpdateComponent implements OnInit {
  isSaving = false;
  fabricLabels: IFabricLabels | null = null;

  editForm: FabricLabelsFormGroup = this.fabricLabelsFormService.createFabricLabelsFormGroup();

  constructor(
    protected fabricLabelsService: FabricLabelsService,
    protected fabricLabelsFormService: FabricLabelsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricLabels }) => {
      this.fabricLabels = fabricLabels;
      if (fabricLabels) {
        this.updateForm(fabricLabels);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabricLabels = this.fabricLabelsFormService.getFabricLabels(this.editForm);
    if (fabricLabels.id !== null) {
      this.subscribeToSaveResponse(this.fabricLabelsService.update(fabricLabels));
    } else {
      this.subscribeToSaveResponse(this.fabricLabelsService.create(fabricLabels));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabricLabels>>): void {
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

  protected updateForm(fabricLabels: IFabricLabels): void {
    this.fabricLabels = fabricLabels;
    this.fabricLabelsFormService.resetForm(this.editForm, fabricLabels);
  }
}
