import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FabricSellerComponent } from './list/fabric-seller.component';
import { FabricSellerDetailComponent } from './detail/fabric-seller-detail.component';
import { FabricSellerUpdateComponent } from './update/fabric-seller-update.component';
import { FabricSellerDeleteDialogComponent } from './delete/fabric-seller-delete-dialog.component';
import { FabricSellerRoutingModule } from './route/fabric-seller-routing.module';

@NgModule({
  imports: [SharedModule, FabricSellerRoutingModule],
  declarations: [FabricSellerComponent, FabricSellerDetailComponent, FabricSellerUpdateComponent, FabricSellerDeleteDialogComponent],
})
export class FabricSellerModule {}
