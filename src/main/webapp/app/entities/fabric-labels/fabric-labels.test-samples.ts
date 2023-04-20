import { IFabricLabels, NewFabricLabels } from './fabric-labels.model';

export const sampleWithRequiredData: IFabricLabels = {
  id: 42174,
  name: 'global Generic',
  code: 'lime',
};

export const sampleWithPartialData: IFabricLabels = {
  id: 636,
  name: 'synergize',
  code: 'scalable mesh',
};

export const sampleWithFullData: IFabricLabels = {
  id: 62898,
  name: 'Car transparent',
  code: 'Picardie Avon',
};

export const sampleWithNewData: NewFabricLabels = {
  name: 'orange Extended Virtual',
  code: 'interface deposit panel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
