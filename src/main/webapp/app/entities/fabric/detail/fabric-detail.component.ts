import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFabric } from '../fabric.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-fabric-detail',
  templateUrl: './fabric-detail.component.html',
})
export class FabricDetailComponent implements OnInit {
  fabric: IFabric | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabric }) => {
      this.fabric = fabric;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
