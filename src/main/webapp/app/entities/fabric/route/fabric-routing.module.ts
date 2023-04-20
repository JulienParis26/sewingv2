import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricComponent } from '../list/fabric.component';
import { FabricDetailComponent } from '../detail/fabric-detail.component';
import { FabricUpdateComponent } from '../update/fabric-update.component';
import { FabricRoutingResolveService } from './fabric-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricRoute: Routes = [
  {
    path: '',
    component: FabricComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricDetailComponent,
    resolve: {
      fabric: FabricRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricUpdateComponent,
    resolve: {
      fabric: FabricRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricUpdateComponent,
    resolve: {
      fabric: FabricRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricRoute)],
  exports: [RouterModule],
})
export class FabricRoutingModule {}
