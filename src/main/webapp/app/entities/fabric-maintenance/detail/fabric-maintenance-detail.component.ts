import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFabricMaintenance } from '../fabric-maintenance.model';

@Component({
  selector: 'jhi-fabric-maintenance-detail',
  templateUrl: './fabric-maintenance-detail.component.html',
})
export class FabricMaintenanceDetailComponent implements OnInit {
  fabricMaintenance: IFabricMaintenance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricMaintenance }) => {
      this.fabricMaintenance = fabricMaintenance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
