import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFabricSeller } from '../fabric-seller.model';

@Component({
  selector: 'jhi-fabric-seller-detail',
  templateUrl: './fabric-seller-detail.component.html',
})
export class FabricSellerDetailComponent implements OnInit {
  fabricSeller: IFabricSeller | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricSeller }) => {
      this.fabricSeller = fabricSeller;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
