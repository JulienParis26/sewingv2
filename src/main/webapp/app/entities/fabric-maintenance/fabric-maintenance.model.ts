import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IFabricMaintenance {
  id: number;
  name?: string | null;
  code?: string | null;
  fabrics?: Pick<IFabric, 'id' | 'name'>[] | null;
}

export type NewFabricMaintenance = Omit<IFabricMaintenance, 'id'> & { id: null };
