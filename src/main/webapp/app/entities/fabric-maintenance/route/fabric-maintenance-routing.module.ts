import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricMaintenanceComponent } from '../list/fabric-maintenance.component';
import { FabricMaintenanceDetailComponent } from '../detail/fabric-maintenance-detail.component';
import { FabricMaintenanceUpdateComponent } from '../update/fabric-maintenance-update.component';
import { FabricMaintenanceRoutingResolveService } from './fabric-maintenance-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricMaintenanceRoute: Routes = [
  {
    path: '',
    component: FabricMaintenanceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricMaintenanceDetailComponent,
    resolve: {
      fabricMaintenance: FabricMaintenanceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricMaintenanceUpdateComponent,
    resolve: {
      fabricMaintenance: FabricMaintenanceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricMaintenanceUpdateComponent,
    resolve: {
      fabricMaintenance: FabricMaintenanceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricMaintenanceRoute)],
  exports: [RouterModule],
})
export class FabricMaintenanceRoutingModule {}
