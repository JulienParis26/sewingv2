import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricLabelsComponent } from '../list/fabric-labels.component';
import { FabricLabelsDetailComponent } from '../detail/fabric-labels-detail.component';
import { FabricLabelsUpdateComponent } from '../update/fabric-labels-update.component';
import { FabricLabelsRoutingResolveService } from './fabric-labels-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricLabelsRoute: Routes = [
  {
    path: '',
    component: FabricLabelsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricLabelsDetailComponent,
    resolve: {
      fabricLabels: FabricLabelsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricLabelsUpdateComponent,
    resolve: {
      fabricLabels: FabricLabelsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricLabelsUpdateComponent,
    resolve: {
      fabricLabels: FabricLabelsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricLabelsRoute)],
  exports: [RouterModule],
})
export class FabricLabelsRoutingModule {}
