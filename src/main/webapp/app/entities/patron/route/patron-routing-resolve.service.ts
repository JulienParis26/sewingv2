import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPatron } from '../patron.model';
import { PatronService } from '../service/patron.service';

@Injectable({ providedIn: 'root' })
export class PatronRoutingResolveService implements Resolve<IPatron | null> {
  constructor(protected service: PatronService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatron | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((patron: HttpResponse<IPatron>) => {
          if (patron.body) {
            return of(patron.body);
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
