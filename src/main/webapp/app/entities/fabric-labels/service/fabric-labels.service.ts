import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabricLabels, NewFabricLabels } from '../fabric-labels.model';

export type PartialUpdateFabricLabels = Partial<IFabricLabels> & Pick<IFabricLabels, 'id'>;

export type EntityResponseType = HttpResponse<IFabricLabels>;
export type EntityArrayResponseType = HttpResponse<IFabricLabels[]>;

@Injectable({ providedIn: 'root' })
export class FabricLabelsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabric-labels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabricLabels: NewFabricLabels): Observable<EntityResponseType> {
    return this.http.post<IFabricLabels>(this.resourceUrl, fabricLabels, { observe: 'response' });
  }

  update(fabricLabels: IFabricLabels): Observable<EntityResponseType> {
    return this.http.put<IFabricLabels>(`${this.resourceUrl}/${this.getFabricLabelsIdentifier(fabricLabels)}`, fabricLabels, {
      observe: 'response',
    });
  }

  partialUpdate(fabricLabels: PartialUpdateFabricLabels): Observable<EntityResponseType> {
    return this.http.patch<IFabricLabels>(`${this.resourceUrl}/${this.getFabricLabelsIdentifier(fabricLabels)}`, fabricLabels, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFabricLabels>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFabricLabels[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricLabelsIdentifier(fabricLabels: Pick<IFabricLabels, 'id'>): number {
    return fabricLabels.id;
  }

  compareFabricLabels(o1: Pick<IFabricLabels, 'id'> | null, o2: Pick<IFabricLabels, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricLabelsIdentifier(o1) === this.getFabricLabelsIdentifier(o2) : o1 === o2;
  }

  addFabricLabelsToCollectionIfMissing<Type extends Pick<IFabricLabels, 'id'>>(
    fabricLabelsCollection: Type[],
    ...fabricLabelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabricLabels: Type[] = fabricLabelsToCheck.filter(isPresent);
    if (fabricLabels.length > 0) {
      const fabricLabelsCollectionIdentifiers = fabricLabelsCollection.map(
        fabricLabelsItem => this.getFabricLabelsIdentifier(fabricLabelsItem)!
      );
      const fabricLabelsToAdd = fabricLabels.filter(fabricLabelsItem => {
        const fabricLabelsIdentifier = this.getFabricLabelsIdentifier(fabricLabelsItem);
        if (fabricLabelsCollectionIdentifiers.includes(fabricLabelsIdentifier)) {
          return false;
        }
        fabricLabelsCollectionIdentifiers.push(fabricLabelsIdentifier);
        return true;
      });
      return [...fabricLabelsToAdd, ...fabricLabelsCollection];
    }
    return fabricLabelsCollection;
  }
}
