import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFabricEditor } from '../fabric-editor.model';
import { FabricEditorService } from '../service/fabric-editor.service';

@Injectable({ providedIn: 'root' })
export class FabricEditorRoutingResolveService implements Resolve<IFabricEditor | null> {
  constructor(protected service: FabricEditorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFabricEditor | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fabricEditor: HttpResponse<IFabricEditor>) => {
          if (fabricEditor.body) {
            return of(fabricEditor.body);
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
