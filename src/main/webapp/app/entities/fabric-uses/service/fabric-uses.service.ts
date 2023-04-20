import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabricUses, NewFabricUses } from '../fabric-uses.model';

export type PartialUpdateFabricUses = Partial<IFabricUses> & Pick<IFabricUses, 'id'>;

export type EntityResponseType = HttpResponse<IFabricUses>;
export type EntityArrayResponseType = HttpResponse<IFabricUses[]>;

@Injectable({ providedIn: 'root' })
export class FabricUsesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabric-uses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabricUses: NewFabricUses): Observable<EntityResponseType> {
    return this.http.post<IFabricUses>(this.resourceUrl, fabricUses, { observe: 'response' });
  }

  update(fabricUses: IFabricUses): Observable<EntityResponseType> {
    return this.http.put<IFabricUses>(`${this.resourceUrl}/${this.getFabricUsesIdentifier(fabricUses)}`, fabricUses, {
      observe: 'response',
    });
  }

  partialUpdate(fabricUses: PartialUpdateFabricUses): Observable<EntityResponseType> {
    return this.http.patch<IFabricUses>(`${this.resourceUrl}/${this.getFabricUsesIdentifier(fabricUses)}`, fabricUses, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFabricUses>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFabricUses[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricUsesIdentifier(fabricUses: Pick<IFabricUses, 'id'>): number {
    return fabricUses.id;
  }

  compareFabricUses(o1: Pick<IFabricUses, 'id'> | null, o2: Pick<IFabricUses, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricUsesIdentifier(o1) === this.getFabricUsesIdentifier(o2) : o1 === o2;
  }

  addFabricUsesToCollectionIfMissing<Type extends Pick<IFabricUses, 'id'>>(
    fabricUsesCollection: Type[],
    ...fabricUsesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabricUses: Type[] = fabricUsesToCheck.filter(isPresent);
    if (fabricUses.length > 0) {
      const fabricUsesCollectionIdentifiers = fabricUsesCollection.map(fabricUsesItem => this.getFabricUsesIdentifier(fabricUsesItem)!);
      const fabricUsesToAdd = fabricUses.filter(fabricUsesItem => {
        const fabricUsesIdentifier = this.getFabricUsesIdentifier(fabricUsesItem);
        if (fabricUsesCollectionIdentifiers.includes(fabricUsesIdentifier)) {
          return false;
        }
        fabricUsesCollectionIdentifiers.push(fabricUsesIdentifier);
        return true;
      });
      return [...fabricUsesToAdd, ...fabricUsesCollection];
    }
    return fabricUsesCollection;
  }
}
