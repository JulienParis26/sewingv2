import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IFabricSeller {
  id: number;
  name?: string | null;
  webSite?: string | null;
  description?: string | null;
  fabrics?: Pick<IFabric, 'id' | 'name'>[] | null;
}

export type NewFabricSeller = Omit<IFabricSeller, 'id'> & { id: null };
