import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricComponent } from './list/fabric.component';
import { FabricDetailComponent } from './detail/fabric-detail.component';
import { FabricUpdateComponent } from './update/fabric-update.component';
import { FabricDeleteDialogComponent } from './delete/fabric-delete-dialog.component';
import { FabricRoutingModule } from './route/fabric-routing.module';

@NgModule({
  imports: [SharedModule, FabricRoutingModule],
  declarations: [FabricComponent, FabricDetailComponent, FabricUpdateComponent, FabricDeleteDialogComponent],
})
export class FabricModule {}
