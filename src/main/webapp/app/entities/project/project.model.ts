import dayjs from 'dayjs/esm';
import { IPatron } from 'app/entities/patron/patron.model';
import { IFabric } from 'app/entities/fabric/fabric.model';

export interface IProject {
  id: number;
  name?: string | null;
  ref?: string | null;
  creationDate?: dayjs.Dayjs | null;
  haberdasheryUse?: string | null;
  accessoryUse?: string | null;
  image1?: string | null;
  image1ContentType?: string | null;
  image2?: string | null;
  image2ContentType?: string | null;
  image3?: string | null;
  image3ContentType?: string | null;
  image4?: string | null;
  image4ContentType?: string | null;
  patron?: Pick<IPatron, 'id' | 'name'> | null;
  fabrics?: Pick<IFabric, 'id'>[] | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
