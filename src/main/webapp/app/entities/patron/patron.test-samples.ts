import dayjs from 'dayjs/esm';

import { PatronType } from 'app/entities/enumerations/patron-type.model';
import { Category } from 'app/entities/enumerations/category.model';
import { DifficultLevel } from 'app/entities/enumerations/difficult-level.model';
import { Qualification } from 'app/entities/enumerations/qualification.model';

import { IPatron, NewPatron } from './patron.model';

export const sampleWithRequiredData: IPatron = {
  id: 88972,
  name: 'Cape Tasty',
  carriedPicture1: '../fake-data/blob/hipster.png',
  carriedPicture1ContentType: 'unknown',
};

export const sampleWithPartialData: IPatron = {
  id: 14490,
  name: 'copying',
  type: PatronType['ONLINE'],
  sexe: Category['MAN'],
  buyDate: dayjs('2023-04-18'),
  creator: 'circuit Chair',
  difficultLevel: DifficultLevel['BEGINNER'],
  requiredFootage: 'b',
  requiredLaize: 'Granite Seine optical',
  clothingType: 'application parsing Chili',
  carriedPicture1: '../fake-data/blob/hipster.png',
  carriedPicture1ContentType: 'unknown',
  carriedPicture2: '../fake-data/blob/hipster.png',
  carriedPicture2ContentType: 'unknown',
};

export const sampleWithFullData: IPatron = {
  id: 50989,
  name: 'Handmade',
  ref: 'Small Pastourelle',
  type: PatronType['PAPER'],
  sexe: Category['KIDS'],
  buyDate: dayjs('2023-04-18'),
  publicationDate: dayjs('2023-04-18'),
  creator: 'Multi-channelled Lari',
  difficultLevel: DifficultLevel['BEGINNER'],
  fabricQualification: Qualification['RIGID'],
  requiredFootage: 'Refined Loan Franche-Comté',
  requiredLaize: 'Antigua-et-Barbuda Ergonomic intuitive',
  clothingType: 'Suriname',
  price: 'SQL',
  pictureTechnicalDrawing: '../fake-data/blob/hipster.png',
  pictureTechnicalDrawingContentType: 'unknown',
  carriedPicture1: '../fake-data/blob/hipster.png',
  carriedPicture1ContentType: 'unknown',
  carriedPicture2: '../fake-data/blob/hipster.png',
  carriedPicture2ContentType: 'unknown',
};

export const sampleWithNewData: NewPatron = {
  name: 'cross-media Chicken',
  carriedPicture1: '../fake-data/blob/hipster.png',
  carriedPicture1ContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
