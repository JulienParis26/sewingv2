import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFabricSeller } from '../fabric-seller.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fabric-seller.test-samples';

import { FabricSellerService } from './fabric-seller.service';

const requireRestSample: IFabricSeller = {
  ...sampleWithRequiredData,
};

describe('FabricSeller Service', () => {
  let service: FabricSellerService;
  let httpMock: HttpTestingController;
  let expectedResult: IFabricSeller | IFabricSeller[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FabricSellerService);
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

    it('should create a FabricSeller', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fabricSeller = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fabricSeller).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FabricSeller', () => {
      const fabricSeller = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fabricSeller).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FabricSeller', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FabricSeller', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FabricSeller', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFabricSellerToCollectionIfMissing', () => {
      it('should add a FabricSeller to an empty array', () => {
        const fabricSeller: IFabricSeller = sampleWithRequiredData;
        expectedResult = service.addFabricSellerToCollectionIfMissing([], fabricSeller);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricSeller);
      });

      it('should not add a FabricSeller to an array that contains it', () => {
        const fabricSeller: IFabricSeller = sampleWithRequiredData;
        const fabricSellerCollection: IFabricSeller[] = [
          {
            ...fabricSeller,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFabricSellerToCollectionIfMissing(fabricSellerCollection, fabricSeller);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FabricSeller to an array that doesn't contain it", () => {
        const fabricSeller: IFabricSeller = sampleWithRequiredData;
        const fabricSellerCollection: IFabricSeller[] = [sampleWithPartialData];
        expectedResult = service.addFabricSellerToCollectionIfMissing(fabricSellerCollection, fabricSeller);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricSeller);
      });

      it('should add only unique FabricSeller to an array', () => {
        const fabricSellerArray: IFabricSeller[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fabricSellerCollection: IFabricSeller[] = [sampleWithRequiredData];
        expectedResult = service.addFabricSellerToCollectionIfMissing(fabricSellerCollection, ...fabricSellerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fabricSeller: IFabricSeller = sampleWithRequiredData;
        const fabricSeller2: IFabricSeller = sampleWithPartialData;
        expectedResult = service.addFabricSellerToCollectionIfMissing([], fabricSeller, fabricSeller2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricSeller);
        expect(expectedResult).toContain(fabricSeller2);
      });

      it('should accept null and undefined values', () => {
        const fabricSeller: IFabricSeller = sampleWithRequiredData;
        expectedResult = service.addFabricSellerToCollectionIfMissing([], null, fabricSeller, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricSeller);
      });

      it('should return initial array if no FabricSeller is added', () => {
        const fabricSellerCollection: IFabricSeller[] = [sampleWithRequiredData];
        expectedResult = service.addFabricSellerToCollectionIfMissing(fabricSellerCollection, undefined, null);
        expect(expectedResult).toEqual(fabricSellerCollection);
      });
    });

    describe('compareFabricSeller', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFabricSeller(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFabricSeller(entity1, entity2);
        const compareResult2 = service.compareFabricSeller(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFabricSeller(entity1, entity2);
        const compareResult2 = service.compareFabricSeller(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFabricSeller(entity1, entity2);
        const compareResult2 = service.compareFabricSeller(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
