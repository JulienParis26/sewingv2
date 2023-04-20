import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IFabricLabels {
  id: number;
  name?: string | null;
  code?: string | null;
  fabrics?: Pick<IFabric, 'id' | 'name'>[] | null;
}

export type NewFabricLabels = Omit<IFabricLabels, 'id'> & { id: null };
