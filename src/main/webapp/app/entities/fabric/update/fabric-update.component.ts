import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FabricFormService, FabricFormGroup } from './fabric-form.service';
import { IFabric } from '../fabric.model';
import { FabricService } from '../service/fabric.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IFabricEditor } from 'app/entities/fabric-editor/fabric-editor.model';
import { FabricEditorService } from 'app/entities/fabric-editor/service/fabric-editor.service';
import { IFabricTypes } from 'app/entities/fabric-types/fabric-types.model';
import { FabricTypesService } from 'app/entities/fabric-types/service/fabric-types.service';
import { IMaterials } from 'app/entities/materials/materials.model';
import { MaterialsService } from 'app/entities/materials/service/materials.service';
import { IFabricUses } from 'app/entities/fabric-uses/fabric-uses.model';
import { FabricUsesService } from 'app/entities/fabric-uses/service/fabric-uses.service';
import { IFabricMaintenance } from 'app/entities/fabric-maintenance/fabric-maintenance.model';
import { FabricMaintenanceService } from 'app/entities/fabric-maintenance/service/fabric-maintenance.service';
import { IFabricLabels } from 'app/entities/fabric-labels/fabric-labels.model';
import { FabricLabelsService } from 'app/entities/fabric-labels/service/fabric-labels.service';

@Component({
  selector: 'jhi-fabric-update',
  templateUrl: './fabric-update.component.html',
})
export class FabricUpdateComponent implements OnInit {
  isSaving = false;
  fabric: IFabric | null = null;

  fabricEditorsSharedCollection: IFabricEditor[] = [];
  fabricTypesSharedCollection: IFabricTypes[] = [];
  materialsSharedCollection: IMaterials[] = [];
  fabricUsesSharedCollection: IFabricUses[] = [];
  fabricMaintenancesSharedCollection: IFabricMaintenance[] = [];
  fabricLabelsSharedCollection: IFabricLabels[] = [];

  editForm: FabricFormGroup = this.fabricFormService.createFabricFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fabricService: FabricService,
    protected fabricFormService: FabricFormService,
    protected fabricEditorService: FabricEditorService,
    protected fabricTypesService: FabricTypesService,
    protected materialsService: MaterialsService,
    protected fabricUsesService: FabricUsesService,
    protected fabricMaintenanceService: FabricMaintenanceService,
    protected fabricLabelsService: FabricLabelsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFabricEditor = (o1: IFabricEditor | null, o2: IFabricEditor | null): boolean =>
    this.fabricEditorService.compareFabricEditor(o1, o2);

  compareFabricTypes = (o1: IFabricTypes | null, o2: IFabricTypes | null): boolean => this.fabricTypesService.compareFabricTypes(o1, o2);

  compareMaterials = (o1: IMaterials | null, o2: IMaterials | null): boolean => this.materialsService.compareMaterials(o1, o2);

  compareFabricUses = (o1: IFabricUses | null, o2: IFabricUses | null): boolean => this.fabricUsesService.compareFabricUses(o1, o2);

  compareFabricMaintenance = (o1: IFabricMaintenance | null, o2: IFabricMaintenance | null): boolean =>
    this.fabricMaintenanceService.compareFabricMaintenance(o1, o2);

  compareFabricLabels = (o1: IFabricLabels | null, o2: IFabricLabels | null): boolean =>
    this.fabricLabelsService.compareFabricLabels(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabric }) => {
      this.fabric = fabric;
      if (fabric) {
        this.updateForm(fabric);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('couturev2App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabric = this.fabricFormService.getFabric(this.editForm);
    if (fabric.id !== null) {
      this.subscribeToSaveResponse(this.fabricService.update(fabric));
    } else {
      this.subscribeToSaveResponse(this.fabricService.create(fabric));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabric>>): void {
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

  protected updateForm(fabric: IFabric): void {
    this.fabric = fabric;
    this.fabricFormService.resetForm(this.editForm, fabric);

    this.fabricEditorsSharedCollection = this.fabricEditorService.addFabricEditorToCollectionIfMissing<IFabricEditor>(
      this.fabricEditorsSharedCollection,
      fabric.from
    );
    this.fabricTypesSharedCollection = this.fabricTypesService.addFabricTypesToCollectionIfMissing<IFabricTypes>(
      this.fabricTypesSharedCollection,
      ...(fabric.fabricTypes ?? [])
    );
    this.materialsSharedCollection = this.materialsService.addMaterialsToCollectionIfMissing<IMaterials>(
      this.materialsSharedCollection,
      ...(fabric.materials ?? [])
    );
    this.fabricUsesSharedCollection = this.fabricUsesService.addFabricUsesToCollectionIfMissing<IFabricUses>(
      this.fabricUsesSharedCollection,
      ...(fabric.uses ?? [])
    );
    this.fabricMaintenancesSharedCollection = this.fabricMaintenanceService.addFabricMaintenanceToCollectionIfMissing<IFabricMaintenance>(
      this.fabricMaintenancesSharedCollection,
      ...(fabric.maintenances ?? [])
    );
    this.fabricLabelsSharedCollection = this.fabricLabelsService.addFabricLabelsToCollectionIfMissing<IFabricLabels>(
      this.fabricLabelsSharedCollection,
      ...(fabric.labels ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fabricEditorService
      .query()
      .pipe(map((res: HttpResponse<IFabricEditor[]>) => res.body ?? []))
      .pipe(
        map((fabricEditors: IFabricEditor[]) =>
          this.fabricEditorService.addFabricEditorToCollectionIfMissing<IFabricEditor>(fabricEditors, this.fabric?.from)
        )
      )
      .subscribe((fabricEditors: IFabricEditor[]) => (this.fabricEditorsSharedCollection = fabricEditors));

    this.fabricTypesService
      .query()
      .pipe(map((res: HttpResponse<IFabricTypes[]>) => res.body ?? []))
      .pipe(
        map((fabricTypes: IFabricTypes[]) =>
          this.fabricTypesService.addFabricTypesToCollectionIfMissing<IFabricTypes>(fabricTypes, ...(this.fabric?.fabricTypes ?? []))
        )
      )
      .subscribe((fabricTypes: IFabricTypes[]) => (this.fabricTypesSharedCollection = fabricTypes));

    this.materialsService
      .query()
      .pipe(map((res: HttpResponse<IMaterials[]>) => res.body ?? []))
      .pipe(
        map((materials: IMaterials[]) =>
          this.materialsService.addMaterialsToCollectionIfMissing<IMaterials>(materials, ...(this.fabric?.materials ?? []))
        )
      )
      .subscribe((materials: IMaterials[]) => (this.materialsSharedCollection = materials));

    this.fabricUsesService
      .query()
      .pipe(map((res: HttpResponse<IFabricUses[]>) => res.body ?? []))
      .pipe(
        map((fabricUses: IFabricUses[]) =>
          this.fabricUsesService.addFabricUsesToCollectionIfMissing<IFabricUses>(fabricUses, ...(this.fabric?.uses ?? []))
        )
      )
      .subscribe((fabricUses: IFabricUses[]) => (this.fabricUsesSharedCollection = fabricUses));

    this.fabricMaintenanceService
      .query()
      .pipe(map((res: HttpResponse<IFabricMaintenance[]>) => res.body ?? []))
      .pipe(
        map((fabricMaintenances: IFabricMaintenance[]) =>
          this.fabricMaintenanceService.addFabricMaintenanceToCollectionIfMissing<IFabricMaintenance>(
            fabricMaintenances,
            ...(this.fabric?.maintenances ?? [])
          )
        )
      )
      .subscribe((fabricMaintenances: IFabricMaintenance[]) => (this.fabricMaintenancesSharedCollection = fabricMaintenances));

    this.fabricLabelsService
      .query()
      .pipe(map((res: HttpResponse<IFabricLabels[]>) => res.body ?? []))
      .pipe(
        map((fabricLabels: IFabricLabels[]) =>
          this.fabricLabelsService.addFabricLabelsToCollectionIfMissing<IFabricLabels>(fabricLabels, ...(this.fabric?.labels ?? []))
        )
      )
      .subscribe((fabricLabels: IFabricLabels[]) => (this.fabricLabelsSharedCollection = fabricLabels));
  }
}
