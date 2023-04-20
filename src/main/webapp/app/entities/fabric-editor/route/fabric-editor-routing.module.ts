import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FabricEditorComponent } from '../list/fabric-editor.component';
import { FabricEditorDetailComponent } from '../detail/fabric-editor-detail.component';
import { FabricEditorUpdateComponent } from '../update/fabric-editor-update.component';
import { FabricEditorRoutingResolveService } from './fabric-editor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fabricEditorRoute: Routes = [
  {
    path: '',
    component: FabricEditorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FabricEditorDetailComponent,
    resolve: {
      fabricEditor: FabricEditorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FabricEditorUpdateComponent,
    resolve: {
      fabricEditor: FabricEditorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FabricEditorUpdateComponent,
    resolve: {
      fabricEditor: FabricEditorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fabricEditorRoute)],
  exports: [RouterModule],
})
export class FabricEditorRoutingModule {}
