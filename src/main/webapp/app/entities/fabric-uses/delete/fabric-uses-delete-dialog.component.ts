import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFabricUses } from '../fabric-uses.model';
import { FabricUsesService } from '../service/fabric-uses.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fabric-uses-delete-dialog.component.html',
})
export class FabricUsesDeleteDialogComponent {
  fabricUses?: IFabricUses;

  constructor(protected fabricUsesService: FabricUsesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fabricUsesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
