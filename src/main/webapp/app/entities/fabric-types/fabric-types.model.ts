import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IFabricTypes {
  id: number;
  name?: string | null;
  code?: string | null;
  description?: string | null;
  fabrics?: Pick<IFabric, 'id' | 'name'>[] | null;
}

export type NewFabricTypes = Omit<IFabricTypes, 'id'> & { id: null };
