import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFabricUses } from '../fabric-uses.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fabric-uses.test-samples';

import { FabricUsesService } from './fabric-uses.service';

const requireRestSample: IFabricUses = {
  ...sampleWithRequiredData,
};

describe('FabricUses Service', () => {
  let service: FabricUsesService;
  let httpMock: HttpTestingController;
  let expectedResult: IFabricUses | IFabricUses[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FabricUsesService);
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

    it('should create a FabricUses', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fabricUses = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fabricUses).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FabricUses', () => {
      const fabricUses = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fabricUses).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FabricUses', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FabricUses', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FabricUses', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFabricUsesToCollectionIfMissing', () => {
      it('should add a FabricUses to an empty array', () => {
        const fabricUses: IFabricUses = sampleWithRequiredData;
        expectedResult = service.addFabricUsesToCollectionIfMissing([], fabricUses);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricUses);
      });

      it('should not add a FabricUses to an array that contains it', () => {
        const fabricUses: IFabricUses = sampleWithRequiredData;
        const fabricUsesCollection: IFabricUses[] = [
          {
            ...fabricUses,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFabricUsesToCollectionIfMissing(fabricUsesCollection, fabricUses);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FabricUses to an array that doesn't contain it", () => {
        const fabricUses: IFabricUses = sampleWithRequiredData;
        const fabricUsesCollection: IFabricUses[] = [sampleWithPartialData];
        expectedResult = service.addFabricUsesToCollectionIfMissing(fabricUsesCollection, fabricUses);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricUses);
      });

      it('should add only unique FabricUses to an array', () => {
        const fabricUsesArray: IFabricUses[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fabricUsesCollection: IFabricUses[] = [sampleWithRequiredData];
        expectedResult = service.addFabricUsesToCollectionIfMissing(fabricUsesCollection, ...fabricUsesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fabricUses: IFabricUses = sampleWithRequiredData;
        const fabricUses2: IFabricUses = sampleWithPartialData;
        expectedResult = service.addFabricUsesToCollectionIfMissing([], fabricUses, fabricUses2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricUses);
        expect(expectedResult).toContain(fabricUses2);
      });

      it('should accept null and undefined values', () => {
        const fabricUses: IFabricUses = sampleWithRequiredData;
        expectedResult = service.addFabricUsesToCollectionIfMissing([], null, fabricUses, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricUses);
      });

      it('should return initial array if no FabricUses is added', () => {
        const fabricUsesCollection: IFabricUses[] = [sampleWithRequiredData];
        expectedResult = service.addFabricUsesToCollectionIfMissing(fabricUsesCollection, undefined, null);
        expect(expectedResult).toEqual(fabricUsesCollection);
      });
    });

    describe('compareFabricUses', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFabricUses(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFabricUses(entity1, entity2);
        const compareResult2 = service.compareFabricUses(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFabricUses(entity1, entity2);
        const compareResult2 = service.compareFabricUses(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFabricUses(entity1, entity2);
        const compareResult2 = service.compareFabricUses(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
