import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabricEditor, NewFabricEditor } from '../fabric-editor.model';

export type PartialUpdateFabricEditor = Partial<IFabricEditor> & Pick<IFabricEditor, 'id'>;

type RestOf<T extends IFabricEditor | NewFabricEditor> = Omit<T, 'printDate'> & {
  printDate?: string | null;
};

export type RestFabricEditor = RestOf<IFabricEditor>;

export type NewRestFabricEditor = RestOf<NewFabricEditor>;

export type PartialUpdateRestFabricEditor = RestOf<PartialUpdateFabricEditor>;

export type EntityResponseType = HttpResponse<IFabricEditor>;
export type EntityArrayResponseType = HttpResponse<IFabricEditor[]>;

@Injectable({ providedIn: 'root' })
export class FabricEditorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabric-editors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabricEditor: NewFabricEditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fabricEditor);
    return this.http
      .post<RestFabricEditor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(fabricEditor: IFabricEditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fabricEditor);
    return this.http
      .put<RestFabricEditor>(`${this.resourceUrl}/${this.getFabricEditorIdentifier(fabricEditor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(fabricEditor: PartialUpdateFabricEditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fabricEditor);
    return this.http
      .patch<RestFabricEditor>(`${this.resourceUrl}/${this.getFabricEditorIdentifier(fabricEditor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFabricEditor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFabricEditor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricEditorIdentifier(fabricEditor: Pick<IFabricEditor, 'id'>): number {
    return fabricEditor.id;
  }

  compareFabricEditor(o1: Pick<IFabricEditor, 'id'> | null, o2: Pick<IFabricEditor, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricEditorIdentifier(o1) === this.getFabricEditorIdentifier(o2) : o1 === o2;
  }

  addFabricEditorToCollectionIfMissing<Type extends Pick<IFabricEditor, 'id'>>(
    fabricEditorCollection: Type[],
    ...fabricEditorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabricEditors: Type[] = fabricEditorsToCheck.filter(isPresent);
    if (fabricEditors.length > 0) {
      const fabricEditorCollectionIdentifiers = fabricEditorCollection.map(
        fabricEditorItem => this.getFabricEditorIdentifier(fabricEditorItem)!
      );
      const fabricEditorsToAdd = fabricEditors.filter(fabricEditorItem => {
        const fabricEditorIdentifier = this.getFabricEditorIdentifier(fabricEditorItem);
        if (fabricEditorCollectionIdentifiers.includes(fabricEditorIdentifier)) {
          return false;
        }
        fabricEditorCollectionIdentifiers.push(fabricEditorIdentifier);
        return true;
      });
      return [...fabricEditorsToAdd, ...fabricEditorCollection];
    }
    return fabricEditorCollection;
  }

  protected convertDateFromClient<T extends IFabricEditor | NewFabricEditor | PartialUpdateFabricEditor>(fabricEditor: T): RestOf<T> {
    return {
      ...fabricEditor,
      printDate: fabricEditor.printDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFabricEditor: RestFabricEditor): IFabricEditor {
    return {
      ...restFabricEditor,
      printDate: restFabricEditor.printDate ? dayjs(restFabricEditor.printDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFabricEditor>): HttpResponse<IFabricEditor> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFabricEditor[]>): HttpResponse<IFabricEditor[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
