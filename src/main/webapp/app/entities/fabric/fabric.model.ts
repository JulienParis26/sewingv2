import dayjs from 'dayjs/esm';
import { IFabricEditor } from 'app/entities/fabric-editor/fabric-editor.model';
import { IFabricTypes } from 'app/entities/fabric-types/fabric-types.model';
import { IMaterials } from 'app/entities/materials/materials.model';
import { IFabricUses } from 'app/entities/fabric-uses/fabric-uses.model';
import { IFabricMaintenance } from 'app/entities/fabric-maintenance/fabric-maintenance.model';
import { IFabricLabels } from 'app/entities/fabric-labels/fabric-labels.model';
import { IFabricSeller } from 'app/entities/fabric-seller/fabric-seller.model';
import { IProject } from 'app/entities/project/project.model';

export interface IFabric {
  id: number;
  name?: string | null;
  ref?: string | null;
  uni?: boolean | null;
  buySize?: string | null;
  elastic?: boolean | null;
  elasticRate?: number | null;
  rating?: number | null;
  colorName?: string | null;
  color1?: string | null;
  color2?: string | null;
  color3?: string | null;
  laize?: number | null;
  meterPrice?: number | null;
  meterBuy?: number | null;
  meterInStock?: number | null;
  buyDate?: dayjs.Dayjs | null;
  gramPerMeter?: number | null;
  sizeMin?: number | null;
  sizeMax?: number | null;
  image1?: string | null;
  image1ContentType?: string | null;
  image2?: string | null;
  image2ContentType?: string | null;
  image3?: string | null;
  image3ContentType?: string | null;
  from?: Pick<IFabricEditor, 'id' | 'name'> | null;
  fabricTypes?: Pick<IFabricTypes, 'id' | 'name'>[] | null;
  materials?: Pick<IMaterials, 'id' | 'name'>[] | null;
  uses?: Pick<IFabricUses, 'id' | 'name'>[] | null;
  maintenances?: Pick<IFabricMaintenance, 'id' | 'name'>[] | null;
  labels?: Pick<IFabricLabels, 'id' | 'name'>[] | null;
  sellers?: Pick<IFabricSeller, 'id' | 'name'>[] | null;
  projects?: Pick<IProject, 'id' | 'name'>[] | null;
}

export type NewFabric = Omit<IFabric, 'id'> & { id: null };
