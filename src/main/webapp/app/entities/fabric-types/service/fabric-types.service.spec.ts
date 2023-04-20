import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFabricTypes } from '../fabric-types.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fabric-types.test-samples';

import { FabricTypesService } from './fabric-types.service';

const requireRestSample: IFabricTypes = {
  ...sampleWithRequiredData,
};

describe('FabricTypes Service', () => {
  let service: FabricTypesService;
  let httpMock: HttpTestingController;
  let expectedResult: IFabricTypes | IFabricTypes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FabricTypesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FabricTypes', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fabricTypes = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fabricTypes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FabricTypes', () => {
      const fabricTypes = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fabricTypes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FabricTypes', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FabricTypes', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FabricTypes', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFabricTypesToCollectionIfMissing', () => {
      it('should add a FabricTypes to an empty array', () => {
        const fabricTypes: IFabricTypes = sampleWithRequiredData;
        expectedResult = service.addFabricTypesToCollectionIfMissing([], fabricTypes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricTypes);
      });

      it('should not add a FabricTypes to an array that contains it', () => {
        const fabricTypes: IFabricTypes = sampleWithRequiredData;
        const fabricTypesCollection: IFabricTypes[] = [
          {
            ...fabricTypes,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFabricTypesToCollectionIfMissing(fabricTypesCollection, fabricTypes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FabricTypes to an array that doesn't contain it", () => {
        const fabricTypes: IFabricTypes = sampleWithRequiredData;
        const fabricTypesCollection: IFabricTypes[] = [sampleWithPartialData];
        expectedResult = service.addFabricTypesToCollectionIfMissing(fabricTypesCollection, fabricTypes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricTypes);
      });

      it('should add only unique FabricTypes to an array', () => {
        const fabricTypesArray: IFabricTypes[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fabricTypesCollection: IFabricTypes[] = [sampleWithRequiredData];
        expectedResult = service.addFabricTypesToCollectionIfMissing(fabricTypesCollection, ...fabricTypesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fabricTypes: IFabricTypes = sampleWithRequiredData;
        const fabricTypes2: IFabricTypes = sampleWithPartialData;
        expectedResult = service.addFabricTypesToCollectionIfMissing([], fabricTypes, fabricTypes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricTypes);
        expect(expectedResult).toContain(fabricTypes2);
      });

      it('should accept null and undefined values', () => {
        const fabricTypes: IFabricTypes = sampleWithRequiredData;
        expectedResult = service.addFabricTypesToCollectionIfMissing([], null, fabricTypes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricTypes);
      });

      it('should return initial array if no FabricTypes is added', () => {
        const fabricTypesCollection: IFabricTypes[] = [sampleWithRequiredData];
        expectedResult = service.addFabricTypesToCollectionIfMissing(fabricTypesCollection, undefined, null);
        expect(expectedResult).toEqual(fabricTypesCollection);
      });
    });

    describe('compareFabricTypes', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFabricTypes(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFabricTypes(entity1, entity2);
        const compareResult2 = service.compareFabricTypes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFabricTypes(entity1, entity2);
        const compareResult2 = service.compareFabricTypes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFabricTypes(entity1, entity2);
        const compareResult2 = service.compareFabricTypes(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
