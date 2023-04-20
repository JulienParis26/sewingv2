import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricLabelsComponent } from './list/fabric-labels.component';
import { FabricLabelsDetailComponent } from './detail/fabric-labels-detail.component';
import { FabricLabelsUpdateComponent } from './update/fabric-labels-update.component';
import { FabricLabelsDeleteDialogComponent } from './delete/fabric-labels-delete-dialog.component';
import { FabricLabelsRoutingModule } from './route/fabric-labels-routing.module';

@NgModule({
  imports: [SharedModule, FabricLabelsRoutingModule],
  declarations: [FabricLabelsComponent, FabricLabelsDetailComponent, FabricLabelsUpdateComponent, FabricLabelsDeleteDialogComponent],
})
export class FabricLabelsModule {}
