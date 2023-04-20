import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaterials } from '../materials.model';

@Component({
  selector: 'jhi-materials-detail',
  templateUrl: './materials-detail.component.html',
})
export class MaterialsDetailComponent implements OnInit {
  materials: IMaterials | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materials }) => {
      this.materials = materials;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
