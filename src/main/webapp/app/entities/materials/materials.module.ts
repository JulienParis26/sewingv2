import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MaterialsComponent } from './list/materials.component';
import { MaterialsDetailComponent } from './detail/materials-detail.component';
import { MaterialsUpdateComponent } from './update/materials-update.component';
import { MaterialsDeleteDialogComponent } from './delete/materials-delete-dialog.component';
import { MaterialsRoutingModule } from './route/materials-routing.module';

@NgModule({
  imports: [SharedModule, MaterialsRoutingModule],
  declarations: [MaterialsComponent, MaterialsDetailComponent, MaterialsUpdateComponent, MaterialsDeleteDialogComponent],
})
export class MaterialsModule {}
