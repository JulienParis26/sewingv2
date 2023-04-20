import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricMaintenanceComponent } from './list/fabric-maintenance.component';
import { FabricMaintenanceDetailComponent } from './detail/fabric-maintenance-detail.component';
import { FabricMaintenanceUpdateComponent } from './update/fabric-maintenance-update.component';
import { FabricMaintenanceDeleteDialogComponent } from './delete/fabric-maintenance-delete-dialog.component';
import { FabricMaintenanceRoutingModule } from './route/fabric-maintenance-routing.module';

@NgModule({
  imports: [SharedModule, FabricMaintenanceRoutingModule],
  declarations: [
    FabricMaintenanceComponent,
    FabricMaintenanceDetailComponent,
    FabricMaintenanceUpdateComponent,
    FabricMaintenanceDeleteDialogComponent,
  ],
})
export class FabricMaintenanceModule {}
