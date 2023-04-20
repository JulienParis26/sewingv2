import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFabricMaintenance, NewFabricMaintenance } from '../fabric-maintenance.model';

export type PartialUpdateFabricMaintenance = Partial<IFabricMaintenance> & Pick<IFabricMaintenance, 'id'>;

export type EntityResponseType = HttpResponse<IFabricMaintenance>;
export type EntityArrayResponseType = HttpResponse<IFabricMaintenance[]>;

@Injectable({ providedIn: 'root' })
export class FabricMaintenanceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fabric-maintenances');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fabricMaintenance: NewFabricMaintenance): Observable<EntityResponseType> {
    return this.http.post<IFabricMaintenance>(this.resourceUrl, fabricMaintenance, { observe: 'response' });
  }

  update(fabricMaintenance: IFabricMaintenance): Observable<EntityResponseType> {
    return this.http.put<IFabricMaintenance>(
      `${this.resourceUrl}/${this.getFabricMaintenanceIdentifier(fabricMaintenance)}`,
      fabricMaintenance,
      { observe: 'response' }
    );
  }

  partialUpdate(fabricMaintenance: PartialUpdateFabricMaintenance): Observable<EntityResponseType> {
    return this.http.patch<IFabricMaintenance>(
      `${this.resourceUrl}/${this.getFabricMaintenanceIdentifier(fabricMaintenance)}`,
      fabricMaintenance,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFabricMaintenance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFabricMaintenance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFabricMaintenanceIdentifier(fabricMaintenance: Pick<IFabricMaintenance, 'id'>): number {
    return fabricMaintenance.id;
  }

  compareFabricMaintenance(o1: Pick<IFabricMaintenance, 'id'> | null, o2: Pick<IFabricMaintenance, 'id'> | null): boolean {
    return o1 && o2 ? this.getFabricMaintenanceIdentifier(o1) === this.getFabricMaintenanceIdentifier(o2) : o1 === o2;
  }

  addFabricMaintenanceToCollectionIfMissing<Type extends Pick<IFabricMaintenance, 'id'>>(
    fabricMaintenanceCollection: Type[],
    ...fabricMaintenancesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fabricMaintenances: Type[] = fabricMaintenancesToCheck.filter(isPresent);
    if (fabricMaintenances.length > 0) {
      const fabricMaintenanceCollectionIdentifiers = fabricMaintenanceCollection.map(
        fabricMaintenanceItem => this.getFabricMaintenanceIdentifier(fabricMaintenanceItem)!
      );
      const fabricMaintenancesToAdd = fabricMaintenances.filter(fabricMaintenanceItem => {
        const fabricMaintenanceIdentifier = this.getFabricMaintenanceIdentifier(fabricMaintenanceItem);
        if (fabricMaintenanceCollectionIdentifiers.includes(fabricMaintenanceIdentifier)) {
          return false;
        }
        fabricMaintenanceCollectionIdentifiers.push(fabricMaintenanceIdentifier);
        return true;
      });
      return [...fabricMaintenancesToAdd, ...fabricMaintenanceCollection];
    }
    return fabricMaintenanceCollection;
  }
}
