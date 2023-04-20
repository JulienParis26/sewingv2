import dayjs from 'dayjs/esm';

import { IFabric, NewFabric } from './fabric.model';

export const sampleWithRequiredData: IFabric = {
  id: 8188,
  name: 'Metal intuitive Fresh',
  uni: true,
  elastic: false,
  color1: 'calculate hour',
  image1: '../fake-data/blob/hipster.png',
  image1ContentType: 'unknown',
};

export const sampleWithPartialData: IFabric = {
  id: 96267,
  name: 'Books',
  uni: true,
  elastic: false,
  rating: 3,
  color1: 'withdrawal',
  color3: 'Dinar Money',
  laize: 45097,
  meterBuy: 45354,
  meterInStock: 37895,
  buyDate: dayjs('2023-04-19'),
  gramPerMeter: 61584,
  sizeMin: 80980,
  image1: '../fake-data/blob/hipster.png',
  image1ContentType: 'unknown',
  image2: '../fake-data/blob/hipster.png',
  image2ContentType: 'unknown',
  image3: '../fake-data/blob/hipster.png',
  image3ContentType: 'unknown',
};

export const sampleWithFullData: IFabric = {
  id: 65686,
  name: 'maximized',
  ref: 'FTP',
  uni: true,
  buySize: 'Metal Music Credit',
  elastic: false,
  elasticRate: 71938,
  rating: 2,
  colorName: 'copy Lari',
  color1: '6th Sleek black',
  color2: 'TCP',
  color3: 'mobile Champagne-Ardenne b',
  laize: 30890,
  meterPrice: 34023,
  meterBuy: 67388,
  meterInStock: 94668,
  buyDate: dayjs('2023-04-19'),
  gramPerMeter: 50105,
  sizeMin: 45711,
  sizeMax: 25193,
  image1: '../fake-data/blob/hipster.png',
  image1ContentType: 'unknown',
  image2: '../fake-data/blob/hipster.png',
  image2ContentType: 'unknown',
  image3: '../fake-data/blob/hipster.png',
  image3ContentType: 'unknown',
};

export const sampleWithNewData: NewFabric = {
  name: 'Auto gold',
  uni: true,
  elastic: false,
  color1: 'brand Handcrafted',
  image1: '../fake-data/blob/hipster.png',
  image1ContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
