import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricUsesComponent } from '../list/fabric-uses.component';
import { FabricUsesDetailComponent } from '../detail/fabric-uses-detail.component';
import { FabricUsesUpdateComponent } from '../update/fabric-uses-update.component';
import { FabricUsesRoutingResolveService } from './fabric-uses-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricUsesRoute: Routes = [
  {
    path: '',
    component: FabricUsesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricUsesDetailComponent,
    resolve: {
      fabricUses: FabricUsesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricUsesUpdateComponent,
    resolve: {
      fabricUses: FabricUsesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricUsesUpdateComponent,
    resolve: {
      fabricUses: FabricUsesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricUsesRoute)],
  exports: [RouterModule],
})
export class FabricUsesRoutingModule {}
