import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricEditorComponent } from './list/fabric-editor.component';
import { FabricEditorDetailComponent } from './detail/fabric-editor-detail.component';
import { FabricEditorUpdateComponent } from './update/fabric-editor-update.component';
import { FabricEditorDeleteDialogComponent } from './delete/fabric-editor-delete-dialog.component';
import { FabricEditorRoutingModule } from './route/fabric-editor-routing.module';

@NgModule({
  imports: [SharedModule, FabricEditorRoutingModule],
  declarations: [FabricEditorComponent, FabricEditorDetailComponent, FabricEditorUpdateComponent, FabricEditorDeleteDialogComponent],
})
export class FabricEditorModule {}
