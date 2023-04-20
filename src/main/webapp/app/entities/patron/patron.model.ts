import dayjs from 'dayjs/esm';
import { PatronType } from 'app/entities/enumerations/patron-type.model';
import { Category } from 'app/entities/enumerations/category.model';
import { DifficultLevel } from 'app/entities/enumerations/difficult-level.model';
import { Qualification } from 'app/entities/enumerations/qualification.model';

export interface IPatron {
  id: number;
  name?: string | null;
  ref?: string | null;
  type?: PatronType | null;
  sexe?: Category | null;
  buyDate?: dayjs.Dayjs | null;
  publicationDate?: dayjs.Dayjs | null;
  creator?: string | null;
  difficultLevel?: DifficultLevel | null;
  fabricQualification?: Qualification | null;
  requiredFootage?: string | null;
  requiredLaize?: string | null;
  clothingType?: string | null;
  price?: string | null;
  pictureTechnicalDrawing?: string | null;
  pictureTechnicalDrawingContentType?: string | null;
  carriedPicture1?: string | null;
  carriedPicture1ContentType?: string | null;
  carriedPicture2?: string | null;
  carriedPicture2ContentType?: string | null;
}

export type NewPatron = Omit<IPatron, 'id'> & { id: null };
