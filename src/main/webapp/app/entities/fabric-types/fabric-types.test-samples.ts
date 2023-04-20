import { IFabricTypes, NewFabricTypes } from './fabric-types.model';

export const sampleWithRequiredData: IFabricTypes = {
  id: 73503,
  name: 'optical backing User-friendly',
  code: 'Berkshire Delesseux',
};

export const sampleWithPartialData: IFabricTypes = {
  id: 22342,
  name: 'SAS bottom-line Personal',
  code: 'Industrial compress quantifying',
};

export const sampleWithFullData: IFabricTypes = {
  id: 32090,
  name: 'Toys Small',
  code: 'b Grocery Cheese',
  description: 'mint Developpeur',
};

export const sampleWithNewData: NewFabricTypes = {
  name: 'Chips',
  code: 'back Rosiers',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
