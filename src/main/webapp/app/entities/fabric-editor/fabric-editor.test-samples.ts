import dayjs from 'dayjs/esm';

import { Editors } from 'app/entities/enumerations/editors.model';
import { Language } from 'app/entities/enumerations/language.model';

import { IFabricEditor, NewFabricEditor } from './fabric-editor.model';

export const sampleWithRequiredData: IFabricEditor = {
  id: 89204,
};

export const sampleWithPartialData: IFabricEditor = {
  id: 55644,
  name: 'primary',
  editor: Editors['LA_MAISON_VICTOR'],
  language: Language['ENGLISH'],
};

export const sampleWithFullData: IFabricEditor = {
  id: 23639,
  name: 'well-modulated Dollar virtual',
  printDate: dayjs('2023-04-18'),
  number: 'a matrix Mandatory',
  editor: Editors['COUTURE_ACTUELLE'],
  language: Language['SPANISH'],
  price: 47123,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewFabricEditor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
