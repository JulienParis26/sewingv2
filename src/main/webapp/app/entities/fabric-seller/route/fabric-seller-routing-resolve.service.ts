import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabricSeller } from '../fabric-seller.model';
import { FabricSellerService } from '../service/fabric-seller.service';

@Injectable({ providedIn: 'root' })
export class FabricSellerRoutingResolveService implements Resolve<IFabricSeller | null> {
  constructor(protected service: FabricSellerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabricSeller | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabricSeller: HttpResponse<IFabricSeller>) => {
          if (fabricSeller.body) {
            return of(fabricSeller.body);
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
