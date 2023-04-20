import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FabricSellerFormService, FabricSellerFormGroup } from './fabric-seller-form.service';
import { IFabricSeller } from '../fabric-seller.model';
import { FabricSellerService } from '../service/fabric-seller.service';
import { IFabric } from 'app/entities/fabric/fabric.model';
import { FabricService } from 'app/entities/fabric/service/fabric.service';

@Component({
  selector: 'jhi-fabric-seller-update',
  templateUrl: './fabric-seller-update.component.html',
})
export class FabricSellerUpdateComponent implements OnInit {
  isSaving = false;
  fabricSeller: IFabricSeller | null = null;

  fabricsSharedCollection: IFabric[] = [];

  editForm: FabricSellerFormGroup = this.fabricSellerFormService.createFabricSellerFormGroup();

  constructor(
    protected fabricSellerService: FabricSellerService,
    protected fabricSellerFormService: FabricSellerFormService,
    protected fabricService: FabricService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFabric = (o1: IFabric | null, o2: IFabric | null): boolean => this.fabricService.compareFabric(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricSeller }) => {
      this.fabricSeller = fabricSeller;
      if (fabricSeller) {
        this.updateForm(fabricSeller);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabricSeller = this.fabricSellerFormService.getFabricSeller(this.editForm);
    if (fabricSeller.id !== null) {
      this.subscribeToSaveResponse(this.fabricSellerService.update(fabricSeller));
    } else {
      this.subscribeToSaveResponse(this.fabricSellerService.create(fabricSeller));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabricSeller>>): void {
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

  protected updateForm(fabricSeller: IFabricSeller): void {
    this.fabricSeller = fabricSeller;
    this.fabricSellerFormService.resetForm(this.editForm, fabricSeller);

    this.fabricsSharedCollection = this.fabricService.addFabricToCollectionIfMissing<IFabric>(
      this.fabricsSharedCollection,
      ...(fabricSeller.fabrics ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fabricService
      .query()
      .pipe(map((res: HttpResponse<IFabric[]>) => res.body ?? []))
      .pipe(
        map((fabrics: IFabric[]) =>
          this.fabricService.addFabricToCollectionIfMissing<IFabric>(fabrics, ...(this.fabricSeller?.fabrics ?? []))
        )
      )
      .subscribe((fabrics: IFabric[]) => (this.fabricsSharedCollection = fabrics));
  }
}
