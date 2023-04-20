import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricUsesComponent } from './list/fabric-uses.component';
import { FabricUsesDetailComponent } from './detail/fabric-uses-detail.component';
import { FabricUsesUpdateComponent } from './update/fabric-uses-update.component';
import { FabricUsesDeleteDialogComponent } from './delete/fabric-uses-delete-dialog.component';
import { FabricUsesRoutingModule } from './route/fabric-uses-routing.module';

@NgModule({
  imports: [SharedModule, FabricUsesRoutingModule],
  declarations: [FabricUsesComponent, FabricUsesDetailComponent, FabricUsesUpdateComponent, FabricUsesDeleteDialogComponent],
})
export class FabricUsesModule {}
