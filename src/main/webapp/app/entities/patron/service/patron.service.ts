import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPatron, NewPatron } from '../patron.model';

export type PartialUpdatePatron = Partial<IPatron> & Pick<IPatron, 'id'>;

type RestOf<T extends IPatron | NewPatron> = Omit<T, 'buyDate' | 'publicationDate'> & {
  buyDate?: string | null;
  publicationDate?: string | null;
};

export type RestPatron = RestOf<IPatron>;

export type NewRestPatron = RestOf<NewPatron>;

export type PartialUpdateRestPatron = RestOf<PartialUpdatePatron>;

export type EntityResponseType = HttpResponse<IPatron>;
export type EntityArrayResponseType = HttpResponse<IPatron[]>;

@Injectable({ providedIn: 'root' })
export class PatronService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/patrons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(patron: NewPatron): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patron);
    return this.http
      .post<RestPatron>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(patron: IPatron): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patron);
    return this.http
      .put<RestPatron>(`${this.resourceUrl}/${this.getPatronIdentifier(patron)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(patron: PartialUpdatePatron): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patron);
    return this.http
      .patch<RestPatron>(`${this.resourceUrl}/${this.getPatronIdentifier(patron)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPatron>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPatron[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPatronIdentifier(patron: Pick<IPatron, 'id'>): number {
    return patron.id;
  }

  comparePatron(o1: Pick<IPatron, 'id'> | null, o2: Pick<IPatron, 'id'> | null): boolean {
    return o1 && o2 ? this.getPatronIdentifier(o1) === this.getPatronIdentifier(o2) : o1 === o2;
  }

  addPatronToCollectionIfMissing<Type extends Pick<IPatron, 'id'>>(
    patronCollection: Type[],
    ...patronsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const patrons: Type[] = patronsToCheck.filter(isPresent);
    if (patrons.length > 0) {
      const patronCollectionIdentifiers = patronCollection.map(patronItem => this.getPatronIdentifier(patronItem)!);
      const patronsToAdd = patrons.filter(patronItem => {
        const patronIdentifier = this.getPatronIdentifier(patronItem);
        if (patronCollectionIdentifiers.includes(patronIdentifier)) {
          return false;
        }
        patronCollectionIdentifiers.push(patronIdentifier);
        return true;
      });
      return [...patronsToAdd, ...patronCollection];
    }
    return patronCollection;
  }

  protected convertDateFromClient<T extends IPatron | NewPatron | PartialUpdatePatron>(patron: T): RestOf<T> {
    return {
      ...patron,
      buyDate: patron.buyDate?.format(DATE_FORMAT) ?? null,
      publicationDate: patron.publicationDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPatron: RestPatron): IPatron {
    return {
      ...restPatron,
      buyDate: restPatron.buyDate ? dayjs(restPatron.buyDate) : undefined,
      publicationDate: restPatron.publicationDate ? dayjs(restPatron.publicationDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPatron>): HttpResponse<IPatron> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPatron[]>): HttpResponse<IPatron[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
