import { IFabricUses, NewFabricUses } from './fabric-uses.model';

export const sampleWithRequiredData: IFabricUses = {
  id: 17514,
  name: 'Loti Persistent',
  code: 'Account',
};

export const sampleWithPartialData: IFabricUses = {
  id: 27444,
  name: 'withdrawal dominicaine',
  code: 'revolutionary',
};

export const sampleWithFullData: IFabricUses = {
  id: 93140,
  name: 'olive',
  code: 'ROI Licensed',
};

export const sampleWithNewData: NewFabricUses = {
  name: 'application drive',
  code: '1080p solid la',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
