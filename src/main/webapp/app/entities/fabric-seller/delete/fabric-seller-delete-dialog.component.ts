import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFabricSeller } from '../fabric-seller.model';
import { FabricSellerService } from '../service/fabric-seller.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fabric-seller-delete-dialog.component.html',
})
export class FabricSellerDeleteDialogComponent {
  fabricSeller?: IFabricSeller;

  constructor(protected fabricSellerService: FabricSellerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fabricSellerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
