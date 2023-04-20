import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabricMaintenance } from '../fabric-maintenance.model';
import { FabricMaintenanceService } from '../service/fabric-maintenance.service';

@Injectable({ providedIn: 'root' })
export class FabricMaintenanceRoutingResolveService implements Resolve<IFabricMaintenance | null> {
  constructor(protected service: FabricMaintenanceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabricMaintenance | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabricMaintenance: HttpResponse<IFabricMaintenance>) => {
          if (fabricMaintenance.body) {
            return of(fabricMaintenance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
