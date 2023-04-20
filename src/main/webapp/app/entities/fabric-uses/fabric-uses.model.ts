import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IFabricUses {
  id: number;
  name?: string | null;
  code?: string | null;
  fabrics?: Pick<IFabric, 'id' | 'name'>[] | null;
}

export type NewFabricUses = Omit<IFabricUses, 'id'> & { id: null };
