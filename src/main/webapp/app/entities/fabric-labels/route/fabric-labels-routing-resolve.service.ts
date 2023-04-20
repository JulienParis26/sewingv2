import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabricLabels } from '../fabric-labels.model';
import { FabricLabelsService } from '../service/fabric-labels.service';

@Injectable({ providedIn: 'root' })
export class FabricLabelsRoutingResolveService implements Resolve<IFabricLabels | null> {
  constructor(protected service: FabricLabelsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabricLabels | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabricLabels: HttpResponse<IFabricLabels>) => {
          if (fabricLabels.body) {
            return of(fabricLabels.body);
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
