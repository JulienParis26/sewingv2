import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabricUses } from '../fabric-uses.model';
import { FabricUsesService } from '../service/fabric-uses.service';

@Injectable({ providedIn: 'root' })
export class FabricUsesRoutingResolveService implements Resolve<IFabricUses | null> {
  constructor(protected service: FabricUsesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabricUses | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabricUses: HttpResponse<IFabricUses>) => {
          if (fabricUses.body) {
            return of(fabricUses.body);
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
