import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFabricUses } from '../fabric-uses.model';

@Component({
  selector: 'jhi-fabric-uses-detail',
  templateUrl: './fabric-uses-detail.component.html',
})
export class FabricUsesDetailComponent implements OnInit {
  fabricUses: IFabricUses | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricUses }) => {
      this.fabricUses = fabricUses;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
