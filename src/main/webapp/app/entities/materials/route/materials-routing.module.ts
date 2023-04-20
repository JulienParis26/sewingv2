import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MaterialsComponent } from '../list/materials.component';
import { MaterialsDetailComponent } from '../detail/materials-detail.component';
import { MaterialsUpdateComponent } from '../update/materials-update.component';
import { MaterialsRoutingResolveService } from './materials-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const materialsRoute: Routes = [
  {
    path: '',
    component: MaterialsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MaterialsDetailComponent,
    resolve: {
      materials: MaterialsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MaterialsUpdateComponent,
    resolve: {
      materials: MaterialsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MaterialsUpdateComponent,
    resolve: {
      materials: MaterialsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(materialsRoute)],
  exports: [RouterModule],
})
export class MaterialsRoutingModule {}
