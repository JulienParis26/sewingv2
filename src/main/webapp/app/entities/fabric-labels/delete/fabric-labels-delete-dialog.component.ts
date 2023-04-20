import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFabricLabels } from '../fabric-labels.model';
import { FabricLabelsService } from '../service/fabric-labels.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fabric-labels-delete-dialog.component.html',
})
export class FabricLabelsDeleteDialogComponent {
  fabricLabels?: IFabricLabels;

  constructor(protected fabricLabelsService: FabricLabelsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fabricLabelsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
