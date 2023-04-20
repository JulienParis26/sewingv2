import dayjs from 'dayjs/esm';
import { Editors } from 'app/entities/enumerations/editors.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IFabricEditor {
  id: number;
  name?: string | null;
  printDate?: dayjs.Dayjs | null;
  number?: string | null;
  editor?: Editors | null;
  language?: Language | null;
  price?: number | null;
  image?: string | null;
  imageContentType?: string | null;
}

export type NewFabricEditor = Omit<IFabricEditor, 'id'> & { id: null };
