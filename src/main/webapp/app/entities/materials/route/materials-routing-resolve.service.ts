import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMaterials } from '../materials.model';
import { MaterialsService } from '../service/materials.service';

@Injectable({ providedIn: 'root' })
export class MaterialsRoutingResolveService implements Resolve<IMaterials | null> {
  constructor(protected service: MaterialsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMaterials | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((materials: HttpResponse<IMaterials>) => {
          if (materials.body) {
            return of(materials.body);
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
