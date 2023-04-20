import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricSellerComponent } from '../list/fabric-seller.component';
import { FabricSellerDetailComponent } from '../detail/fabric-seller-detail.component';
import { FabricSellerUpdateComponent } from '../update/fabric-seller-update.component';
import { FabricSellerRoutingResolveService } from './fabric-seller-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricSellerRoute: Routes = [
  {
    path: '',
    component: FabricSellerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricSellerDetailComponent,
    resolve: {
      fabricSeller: FabricSellerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricSellerUpdateComponent,
    resolve: {
      fabricSeller: FabricSellerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricSellerUpdateComponent,
    resolve: {
      fabricSeller: FabricSellerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricSellerRoute)],
  exports: [RouterModule],
})
export class FabricSellerRoutingModule {}
