import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFabricLabels } from '../fabric-labels.model';

@Component({
  selector: 'jhi-fabric-labels-detail',
  templateUrl: './fabric-labels-detail.component.html',
})
export class FabricLabelsDetailComponent implements OnInit {
  fabricLabels: IFabricLabels | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricLabels }) => {
      this.fabricLabels = fabricLabels;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
