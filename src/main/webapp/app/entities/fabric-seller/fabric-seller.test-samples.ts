import { IFabricSeller, NewFabricSeller } from './fabric-seller.model';

export const sampleWithRequiredData: IFabricSeller = {
  id: 3151,
  name: 'Saint-Christophe-et-Niévès holistic',
};

export const sampleWithPartialData: IFabricSeller = {
  id: 52914,
  name: 'Baby',
  webSite: 'Agent dynamic Richelieu',
};

export const sampleWithFullData: IFabricSeller = {
  id: 44352,
  name: 'platforms indexing',
  webSite: 'c',
  description: 'a',
};

export const sampleWithNewData: NewFabricSeller = {
  name: 'Borders Tools Haute-Normandie',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
