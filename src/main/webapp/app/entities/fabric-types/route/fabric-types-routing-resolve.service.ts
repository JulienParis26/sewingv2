import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabricTypes } from '../fabric-types.model';
import { FabricTypesService } from '../service/fabric-types.service';

@Injectable({ providedIn: 'root' })
export class FabricTypesRoutingResolveService implements Resolve<IFabricTypes | null> {
  constructor(protected service: FabricTypesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabricTypes | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabricTypes: HttpResponse<IFabricTypes>) => {
          if (fabricTypes.body) {
            return of(fabricTypes.body);
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
