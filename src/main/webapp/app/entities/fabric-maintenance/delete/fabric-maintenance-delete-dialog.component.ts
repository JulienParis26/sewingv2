import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFabricMaintenance } from '../fabric-maintenance.model';
import { FabricMaintenanceService } from '../service/fabric-maintenance.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fabric-maintenance-delete-dialog.component.html',
})
export class FabricMaintenanceDeleteDialogComponent {
  fabricMaintenance?: IFabricMaintenance;

  constructor(protected fabricMaintenanceService: FabricMaintenanceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fabricMaintenanceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
