import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabric } from '../fabric.model';
import { FabricService } from '../service/fabric.service';

@Injectable({ providedIn: 'root' })
export class FabricRoutingResolveService implements Resolve<IFabric | null> {
  constructor(protected service: FabricService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabric | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabric: HttpResponse<IFabric>) => {
          if (fabric.body) {
            return of(fabric.body);
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
