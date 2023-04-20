import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricTypesComponent } from '../list/fabric-types.component';
import { FabricTypesDetailComponent } from '../detail/fabric-types-detail.component';
import { FabricTypesUpdateComponent } from '../update/fabric-types-update.component';
import { FabricTypesRoutingResolveService } from './fabric-types-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricTypesRoute: Routes = [
  {
    path: '',
    component: FabricTypesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricTypesDetailComponent,
    resolve: {
      fabricTypes: FabricTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricTypesUpdateComponent,
    resolve: {
      fabricTypes: FabricTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricTypesUpdateComponent,
    resolve: {
      fabricTypes: FabricTypesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricTypesRoute)],
  exports: [RouterModule],
})
export class FabricTypesRoutingModule {}
