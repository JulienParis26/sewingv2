import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFabricTypes } from '../fabric-types.model';

@Component({
  selector: 'jhi-fabric-types-detail',
  templateUrl: './fabric-types-detail.component.html',
})
export class FabricTypesDetailComponent implements OnInit {
  fabricTypes: IFabricTypes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricTypes }) => {
      this.fabricTypes = fabricTypes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
