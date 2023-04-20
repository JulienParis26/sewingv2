import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabric, NewFabric } from '../fabric.model';

export type PartialUpdateFabric = Partial<IFabric> & Pick<IFabric, 'id'>;

type RestOf<T extends IFabric | NewFabric> = Omit<T, 'buyDate'> & {
  buyDate?: string | null;
};

export type RestFabric = RestOf<IFabric>;

export type NewRestFabric = RestOf<NewFabric>;

export type PartialUpdateRestFabric = RestOf<PartialUpdateFabric>;

export type EntityResponseType = HttpResponse<IFabric>;
export type EntityArrayResponseType = HttpResponse<IFabric[]>;

@Injectable({ providedIn: 'root' })
export class FabricService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabrics');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabric: NewFabric): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fabric);
    return this.http
      .post<RestFabric>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(fabric: IFabric): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fabric);
    return this.http
      .put<RestFabric>(`${this.resourceUrl}/${this.getFabricIdentifier(fabric)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(fabric: PartialUpdateFabric): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fabric);
    return this.http
      .patch<RestFabric>(`${this.resourceUrl}/${this.getFabricIdentifier(fabric)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFabric>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFabric[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricIdentifier(fabric: Pick<IFabric, 'id'>): number {
    return fabric.id;
  }

  compareFabric(o1: Pick<IFabric, 'id'> | null, o2: Pick<IFabric, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricIdentifier(o1) === this.getFabricIdentifier(o2) : o1 === o2;
  }

  addFabricToCollectionIfMissing<Type extends Pick<IFabric, 'id'>>(
    fabricCollection: Type[],
    ...fabricsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabrics: Type[] = fabricsToCheck.filter(isPresent);
    if (fabrics.length > 0) {
      const fabricCollectionIdentifiers = fabricCollection.map(fabricItem => this.getFabricIdentifier(fabricItem)!);
      const fabricsToAdd = fabrics.filter(fabricItem => {
        const fabricIdentifier = this.getFabricIdentifier(fabricItem);
        if (fabricCollectionIdentifiers.includes(fabricIdentifier)) {
          return false;
        }
        fabricCollectionIdentifiers.push(fabricIdentifier);
        return true;
      });
      return [...fabricsToAdd, ...fabricCollection];
    }
    return fabricCollection;
  }

  protected convertDateFromClient<T extends IFabric | NewFabric | PartialUpdateFabric>(fabric: T): RestOf<T> {
    return {
      ...fabric,
      buyDate: fabric.buyDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFabric: RestFabric): IFabric {
    return {
      ...restFabric,
      buyDate: restFabric.buyDate ? dayjs(restFabric.buyDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFabric>): HttpResponse<IFabric> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFabric[]>): HttpResponse<IFabric[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
