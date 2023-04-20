import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IMaterials {
  id: number;
  name?: string | null;
  webSite?: string | null;
  description?: string | null;
  fabrics?: Pick<IFabric, 'id' | 'name'>[] | null;
}

export type NewMaterials = Omit<IMaterials, 'id'> & { id: null };
