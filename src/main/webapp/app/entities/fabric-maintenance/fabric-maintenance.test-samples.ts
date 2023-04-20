import { IFabricMaintenance, NewFabricMaintenance } from './fabric-maintenance.model';

export const sampleWithRequiredData: IFabricMaintenance = {
  id: 9321,
  name: 'compress Gloves morph',
  code: 'engineer program b',
};

export const sampleWithPartialData: IFabricMaintenance = {
  id: 86710,
  name: 'Mauritanie',
  code: 'Technicien Consultant local',
};

export const sampleWithFullData: IFabricMaintenance = {
  id: 73455,
  name: 'Outdoors',
  code: 'Games action-items world-class',
};

export const sampleWithNewData: NewFabricMaintenance = {
  name: 'Garden',
  code: 'payment panel magnetic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
