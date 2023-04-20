import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FabricMaintenanceFormService, FabricMaintenanceFormGroup } from './fabric-maintenance-form.service';
import { IFabricMaintenance } from '../fabric-maintenance.model';
import { FabricMaintenanceService } from '../service/fabric-maintenance.service';

@Component({
  selector: 'jhi-fabric-maintenance-update',
  templateUrl: './fabric-maintenance-update.component.html',
})
export class FabricMaintenanceUpdateComponent implements OnInit {
  isSaving = false;
  fabricMaintenance: IFabricMaintenance | null = null;

  editForm: FabricMaintenanceFormGroup = this.fabricMaintenanceFormService.createFabricMaintenanceFormGroup();

  constructor(
    protected fabricMaintenanceService: FabricMaintenanceService,
    protected fabricMaintenanceFormService: FabricMaintenanceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricMaintenance }) => {
      this.fabricMaintenance = fabricMaintenance;
      if (fabricMaintenance) {
        this.updateForm(fabricMaintenance);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabricMaintenance = this.fabricMaintenanceFormService.getFabricMaintenance(this.editForm);
    if (fabricMaintenance.id !== null) {
      this.subscribeToSaveResponse(this.fabricMaintenanceService.update(fabricMaintenance));
    } else {
      this.subscribeToSaveResponse(this.fabricMaintenanceService.create(fabricMaintenance));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabricMaintenance>>): void {
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

  protected updateForm(fabricMaintenance: IFabricMaintenance): void {
    this.fabricMaintenance = fabricMaintenance;
    this.fabricMaintenanceFormService.resetForm(this.editForm, fabricMaintenance);
  }
}
