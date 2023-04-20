import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricTypesComponent } from './list/fabric-types.component';
import { FabricTypesDetailComponent } from './detail/fabric-types-detail.component';
import { FabricTypesUpdateComponent } from './update/fabric-types-update.component';
import { FabricTypesDeleteDialogComponent } from './delete/fabric-types-delete-dialog.component';
import { FabricTypesRoutingModule } from './route/fabric-types-routing.module';

@NgModule({
  imports: [SharedModule, FabricTypesRoutingModule],
  declarations: [FabricTypesComponent, FabricTypesDetailComponent, FabricTypesUpdateComponent, FabricTypesDeleteDialogComponent],
})
export class FabricTypesModule {}
