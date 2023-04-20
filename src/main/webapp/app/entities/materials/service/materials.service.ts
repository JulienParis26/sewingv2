import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMaterials, NewMaterials } from '../materials.model';

export type PartialUpdateMaterials = Partial<IMaterials> & Pick<IMaterials, 'id'>;

export type EntityResponseType = HttpResponse<IMaterials>;
export type EntityArrayResponseType = HttpResponse<IMaterials[]>;

@Injectable({ providedIn: 'root' })
export class MaterialsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/materials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(materials: NewMaterials): Observable<EntityResponseType> {
    return this.http.post<IMaterials>(this.resourceUrl, materials, { observe: 'response' });
  }

  update(materials: IMaterials): Observable<EntityResponseType> {
    return this.http.put<IMaterials>(`${this.resourceUrl}/${this.getMaterialsIdentifier(materials)}`, materials, { observe: 'response' });
  }

  partialUpdate(materials: PartialUpdateMaterials): Observable<EntityResponseType> {
    return this.http.patch<IMaterials>(`${this.resourceUrl}/${this.getMaterialsIdentifier(materials)}`, materials, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaterials>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaterials[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMaterialsIdentifier(materials: Pick<IMaterials, 'id'>): number {
    return materials.id;
  }

  compareMaterials(o1: Pick<IMaterials, 'id'> | null, o2: Pick<IMaterials, 'id'> | null): boolean {
    return o1 && o2 ? this.getMaterialsIdentifier(o1) === this.getMaterialsIdentifier(o2) : o1 === o2;
  }

  addMaterialsToCollectionIfMissing<Type extends Pick<IMaterials, 'id'>>(
    materialsCollection: Type[],
    ...materialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const materials: Type[] = materialsToCheck.filter(isPresent);
    if (materials.length > 0) {
      const materialsCollectionIdentifiers = materialsCollection.map(materialsItem => this.getMaterialsIdentifier(materialsItem)!);
      const materialsToAdd = materials.filter(materialsItem => {
        const materialsIdentifier = this.getMaterialsIdentifier(materialsItem);
        if (materialsCollectionIdentifiers.includes(materialsIdentifier)) {
          return false;
        }
        materialsCollectionIdentifiers.push(materialsIdentifier);
        return true;
      });
      return [...materialsToAdd, ...materialsCollection];
    }
    return materialsCollection;
  }
}
