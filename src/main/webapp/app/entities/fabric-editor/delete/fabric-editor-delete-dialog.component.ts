import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFabricEditor } from '../fabric-editor.model';
import { FabricEditorService } from '../service/fabric-editor.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fabric-editor-delete-dialog.component.html',
})
export class FabricEditorDeleteDialogComponent {
  fabricEditor?: IFabricEditor;

  constructor(protected fabricEditorService: FabricEditorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fabricEditorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
