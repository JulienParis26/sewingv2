import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabricSeller, NewFabricSeller } from '../fabric-seller.model';

export type PartialUpdateFabricSeller = Partial<IFabricSeller> & Pick<IFabricSeller, 'id'>;

export type EntityResponseType = HttpResponse<IFabricSeller>;
export type EntityArrayResponseType = HttpResponse<IFabricSeller[]>;

@Injectable({ providedIn: 'root' })
export class FabricSellerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabric-sellers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabricSeller: NewFabricSeller): Observable<EntityResponseType> {
    return this.http.post<IFabricSeller>(this.resourceUrl, fabricSeller, { observe: 'response' });
  }

  update(fabricSeller: IFabricSeller): Observable<EntityResponseType> {
    return this.http.put<IFabricSeller>(`${this.resourceUrl}/${this.getFabricSellerIdentifier(fabricSeller)}`, fabricSeller, {
      observe: 'response',
    });
  }

  partialUpdate(fabricSeller: PartialUpdateFabricSeller): Observable<EntityResponseType> {
    return this.http.patch<IFabricSeller>(`${this.resourceUrl}/${this.getFabricSellerIdentifier(fabricSeller)}`, fabricSeller, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFabricSeller>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFabricSeller[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricSellerIdentifier(fabricSeller: Pick<IFabricSeller, 'id'>): number {
    return fabricSeller.id;
  }

  compareFabricSeller(o1: Pick<IFabricSeller, 'id'> | null, o2: Pick<IFabricSeller, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricSellerIdentifier(o1) === this.getFabricSellerIdentifier(o2) : o1 === o2;
  }

  addFabricSellerToCollectionIfMissing<Type extends Pick<IFabricSeller, 'id'>>(
    fabricSellerCollection: Type[],
    ...fabricSellersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabricSellers: Type[] = fabricSellersToCheck.filter(isPresent);
    if (fabricSellers.length > 0) {
      const fabricSellerCollectionIdentifiers = fabricSellerCollection.map(
        fabricSellerItem => this.getFabricSellerIdentifier(fabricSellerItem)!
      );
      const fabricSellersToAdd = fabricSellers.filter(fabricSellerItem => {
        const fabricSellerIdentifier = this.getFabricSellerIdentifier(fabricSellerItem);
        if (fabricSellerCollectionIdentifiers.includes(fabricSellerIdentifier)) {
          return false;
        }
        fabricSellerCollectionIdentifiers.push(fabricSellerIdentifier);
        return true;
      });
      return [...fabricSellersToAdd, ...fabricSellerCollection];
    }
    return fabricSellerCollection;
  }
}
