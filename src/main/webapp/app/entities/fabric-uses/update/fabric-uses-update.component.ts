import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FabricUsesFormService, FabricUsesFormGroup } from './fabric-uses-form.service';
import { IFabricUses } from '../fabric-uses.model';
import { FabricUsesService } from '../service/fabric-uses.service';

@Component({
  selector: 'jhi-fabric-uses-update',
  templateUrl: './fabric-uses-update.component.html',
})
export class FabricUsesUpdateComponent implements OnInit {
  isSaving = false;
  fabricUses: IFabricUses | null = null;

  editForm: FabricUsesFormGroup = this.fabricUsesFormService.createFabricUsesFormGroup();

  constructor(
    protected fabricUsesService: FabricUsesService,
    protected fabricUsesFormService: FabricUsesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricUses }) => {
      this.fabricUses = fabricUses;
      if (fabricUses) {
        this.updateForm(fabricUses);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabricUses = this.fabricUsesFormService.getFabricUses(this.editForm);
    if (fabricUses.id !== null) {
      this.subscribeToSaveResponse(this.fabricUsesService.update(fabricUses));
    } else {
      this.subscribeToSaveResponse(this.fabricUsesService.create(fabricUses));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabricUses>>): void {
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

  protected updateForm(fabricUses: IFabricUses): void {
    this.fabricUses = fabricUses;
    this.fabricUsesFormService.resetForm(this.editForm, fabricUses);
  }
}
