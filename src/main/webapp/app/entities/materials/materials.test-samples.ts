import { IMaterials, NewMaterials } from './materials.model';

export const sampleWithRequiredData: IMaterials = {
  id: 84820,
  name: 'payment overriding',
};

export const sampleWithPartialData: IMaterials = {
  id: 80109,
  name: 'Practical',
  webSite: 'responsive Jewelery',
};

export const sampleWithFullData: IMaterials = {
  id: 7912,
  name: 'recontextualize',
  webSite: 'Berkshire hack',
  description: 'Music parse',
};

export const sampleWithNewData: NewMaterials = {
  name: 'Rubber',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
