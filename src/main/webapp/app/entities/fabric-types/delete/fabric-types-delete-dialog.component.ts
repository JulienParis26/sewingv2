import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFabricTypes } from '../fabric-types.model';
import { FabricTypesService } from '../service/fabric-types.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fabric-types-delete-dialog.component.html',
})
export class FabricTypesDeleteDialogComponent {
  fabricTypes?: IFabricTypes;

  constructor(protected fabricTypesService: FabricTypesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fabricTypesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
