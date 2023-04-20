import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabricTypes, NewFabricTypes } from '../fabric-types.model';

export type PartialUpdateFabricTypes = Partial<IFabricTypes> & Pick<IFabricTypes, 'id'>;

export type EntityResponseType = HttpResponse<IFabricTypes>;
export type EntityArrayResponseType = HttpResponse<IFabricTypes[]>;

@Injectable({ providedIn: 'root' })
export class FabricTypesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabric-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabricTypes: NewFabricTypes): Observable<EntityResponseType> {
    return this.http.post<IFabricTypes>(this.resourceUrl, fabricTypes, { observe: 'response' });
  }

  update(fabricTypes: IFabricTypes): Observable<EntityResponseType> {
    return this.http.put<IFabricTypes>(`${this.resourceUrl}/${this.getFabricTypesIdentifier(fabricTypes)}`, fabricTypes, {
      observe: 'response',
    });
  }

  partialUpdate(fabricTypes: PartialUpdateFabricTypes): Observable<EntityResponseType> {
    return this.http.patch<IFabricTypes>(`${this.resourceUrl}/${this.getFabricTypesIdentifier(fabricTypes)}`, fabricTypes, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFabricTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFabricTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricTypesIdentifier(fabricTypes: Pick<IFabricTypes, 'id'>): number {
    return fabricTypes.id;
  }

  compareFabricTypes(o1: Pick<IFabricTypes, 'id'> | null, o2: Pick<IFabricTypes, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricTypesIdentifier(o1) === this.getFabricTypesIdentifier(o2) : o1 === o2;
  }

  addFabricTypesToCollectionIfMissing<Type extends Pick<IFabricTypes, 'id'>>(
    fabricTypesCollection: Type[],
    ...fabricTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabricTypes: Type[] = fabricTypesToCheck.filter(isPresent);
    if (fabricTypes.length > 0) {
      const fabricTypesCollectionIdentifiers = fabricTypesCollection.map(
        fabricTypesItem => this.getFabricTypesIdentifier(fabricTypesItem)!
      );
      const fabricTypesToAdd = fabricTypes.filter(fabricTypesItem => {
        const fabricTypesIdentifier = this.getFabricTypesIdentifier(fabricTypesItem);
        if (fabricTypesCollectionIdentifiers.includes(fabricTypesIdentifier)) {
          return false;
        }
        fabricTypesCollectionIdentifiers.push(fabricTypesIdentifier);
        return true;
      });
      return [...fabricTypesToAdd, ...fabricTypesCollection];
    }
    return fabricTypesCollection;
  }
}
